package com.receiptfinanceapi.services.impl;

import com.receiptfinanceapi.dtos.*;
import com.receiptfinanceapi.entities.*;
import com.receiptfinanceapi.projection.IReceiptListProjection;
import com.receiptfinanceapi.repository.*;
import com.receiptfinanceapi.services.IReceiptService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReceiptServiceImpl implements IReceiptService {

    private final IReceiptDataRepository receiptDataRepository;
    private final IRateRepository rateRepository;
    private final IRateTermRepository rateTermRepository;
    private final IExpenseRepository expenseRepository;
    private final IExpenseReasonRepository expenseReasonRepository;
    private final ICompoundingPeriodRepository compoundingPeriodRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ReceiptServiceImpl(IReceiptDataRepository receiptDataRepository, IRateRepository rateRepository,
                              IRateTermRepository rateTermRepository, IExpenseRepository expenseRepository,
                              IExpenseReasonRepository expenseReasonRepository,
                              ICompoundingPeriodRepository compoundingPeriodRepository, ModelMapper modelMapper) {
        this.receiptDataRepository = receiptDataRepository;
        this.rateRepository = rateRepository;
        this.rateTermRepository = rateTermRepository;
        this.expenseRepository = expenseRepository;
        this.expenseReasonRepository = expenseReasonRepository;
        this.compoundingPeriodRepository = compoundingPeriodRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    @Transactional
    public ReceiptData createReceipt(ReceiptToCreateRequest receiptToCreateRequest, Long idUser) throws Exception {
        //set Receipt
        ReceiptData receiptDataToCreate = this.modelMapper.map(receiptToCreateRequest, ReceiptData.class);
        receiptDataToCreate.setIdUser(idUser);

        Rate rateToCreate = this.modelMapper.map(receiptToCreateRequest.getRate(), Rate.class);
        receiptDataToCreate.setRate(rateToCreate);

        //set compoundperiod
        CompoundingPeriod compoundingPeriodCreated = null;
        CompoundingPeriod compoundingPeriod = this.modelMapper.map(receiptToCreateRequest.getCompoundingPeriod(), CompoundingPeriod.class);

        if(receiptToCreateRequest.getCompoundingPeriod().getName().equals("Especial")) {
            compoundingPeriod.setId(null);
        }
        compoundingPeriodCreated = this.compoundingPeriodRepository.save(compoundingPeriod);
        receiptDataToCreate.getRate().setIdCompoundingPeriod(compoundingPeriodCreated.getId());

        // set rateterm
        RateTerm rateTermCreated = null;
        RateTerm rateTerm = this.modelMapper.map(receiptToCreateRequest.getRateTerm(), RateTerm.class);

        if(receiptToCreateRequest.getRateTerm().getName().equals("Especial")) {
            rateTerm.setId(null);
        }
        System.out.println(rateTerm.getId());
        System.out.println("Crearemos el reate term");
        rateTermCreated = this.rateTermRepository.save(rateTerm);
        receiptDataToCreate.getRate().setIdRateTerm(rateTermCreated.getId());

        System.out.println("Se creo el rate term");

        //Expenses
        List<ExpenseReason> expenseReasonsToSave =
                receiptToCreateRequest.getExpenses().stream().map(expense -> this.modelMapper.map(expense.getExpenseReason(), ExpenseReason.class)).collect(Collectors.toList());

       List<ExpenseReason> expenseReasonsCreated = this.expenseReasonRepository.saveAll(expenseReasonsToSave);

        List<Long> expenseReasonIds = expenseReasonsCreated.stream().map(erc -> erc.getId()).collect(Collectors.toList());

        receiptDataToCreate.getRate().setCompoundingPeriod(null);
        receiptDataToCreate.getRate().setRateTerm(null);

        Rate rateCreated = this.rateRepository.save(receiptDataToCreate.getRate());

        List<Expense> expensesToSave = receiptToCreateRequest.getExpenses().stream().map(expenseIt -> {
            Expense newExpense = this.modelMapper.map(expenseIt, Expense.class);
            newExpense.setExpenseReason(null);
            newExpense.setIdRate(rateCreated.getId());
            return newExpense;
        }).collect(Collectors.toList());

        for (int i = 0; i < expensesToSave.size(); i++) {
            expensesToSave.get(i).setIdExpenseReason(expenseReasonIds.get(i));
        }

        this.expenseRepository.saveAll(expensesToSave);


        receiptDataToCreate.setRate(null);
        receiptDataToCreate.setIdRate(rateCreated.getId());
        ReceiptData receiptDataCreated = this.receiptDataRepository.save(receiptDataToCreate);

        return receiptDataCreated;
    }

    @Override
    public Page<ReceiptToListInTableResponse> findAllReceiptsByUserId(int pageNumber, int size, Long idUser) throws Exception {
        //Esto es para que sea 20 como m??ximo
        size = Math.min(size, 20);

        // Indicamos como se ordenara
        Pageable pageFiltered = PageRequest.of(pageNumber, size, Sort.by("id").descending());

        //Llamando al native query
        Page<IReceiptListProjection> receiptListProjections = this.receiptDataRepository.publicationsFindedForUserId(idUser, pageFiltered);

        Page<ReceiptToListInTableResponse>receiptToListInTableResponsePage =
                receiptListProjections.map(objectEntity -> this.modelMapper.map(objectEntity, ReceiptToListInTableResponse.class));

        return receiptToListInTableResponsePage;
    }

    @Override
    public ReceiptToGetResponse findOneOfMyReceiptsById(Long idReceipt, Long idUser) throws Exception {
        ReceiptData receiptData = this.receiptDataRepository.findByIdAndIdUser(idReceipt, idUser);

        ReceiptToGetResponse receiptToGetResponse = this.modelMapper.map(receiptData, ReceiptToGetResponse.class);

        receiptToGetResponse.setCompoundingPeriod(receiptData.getRate().getCompoundingPeriod());
        receiptToGetResponse.setExpenses(receiptData.getRate().getExpenses());

        return receiptToGetResponse;
    }
}
