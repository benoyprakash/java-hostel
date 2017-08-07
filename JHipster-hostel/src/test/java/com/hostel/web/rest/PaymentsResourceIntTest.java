package com.hostel.web.rest;

import com.hostel.HostelApp;

import com.hostel.domain.Payments;
import com.hostel.repository.PaymentsRepository;
import com.hostel.service.PaymentsService;
import com.hostel.service.dto.PaymentsDTO;
import com.hostel.service.mapper.PaymentsMapper;
import com.hostel.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hostel.domain.enumeration.PaymentStatus;
import com.hostel.domain.enumeration.PaymentAgainstType;
/**
 * Test class for the PaymentsResource REST controller.
 *
 * @see PaymentsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HostelApp.class)
public class PaymentsResourceIntTest {

    private static final String DEFAULT_ROOM = "AAAAAAAAAA";
    private static final String UPDATED_ROOM = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_JOIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_JOIN = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final LocalDate DEFAULT_PAYMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAYMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PAYMENT_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAYMENT_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PAYMENT_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAYMENT_TO = LocalDate.now(ZoneId.systemDefault());

    private static final PaymentStatus DEFAULT_PAYMENT_STATUS = PaymentStatus.PAID;
    private static final PaymentStatus UPDATED_PAYMENT_STATUS = PaymentStatus.PAID_PARTIAL;

    private static final PaymentAgainstType DEFAULT_PAYENT_AGAINST = PaymentAgainstType.ADVANCE;
    private static final PaymentAgainstType UPDATED_PAYENT_AGAINST = PaymentAgainstType.RENT;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_BUILDING = "AAAAAAAAAA";
    private static final String UPDATED_BUILDING = "BBBBBBBBBB";

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Autowired
    private PaymentsMapper paymentsMapper;

    @Autowired
    private PaymentsService paymentsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restPaymentsMockMvc;

    private Payments payments;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PaymentsResource paymentsResource = new PaymentsResource(paymentsService);
        this.restPaymentsMockMvc = MockMvcBuilders.standaloneSetup(paymentsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payments createEntity() {
        Payments payments = new Payments()
            .room(DEFAULT_ROOM)
            .customer(DEFAULT_CUSTOMER)
            .dateOfJoin(DEFAULT_DATE_OF_JOIN)
            .amount(DEFAULT_AMOUNT)
            .paymentDate(DEFAULT_PAYMENT_DATE)
            .paymentFrom(DEFAULT_PAYMENT_FROM)
            .paymentTo(DEFAULT_PAYMENT_TO)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .payentAgainst(DEFAULT_PAYENT_AGAINST)
            .comments(DEFAULT_COMMENTS)
            .building(DEFAULT_BUILDING);
        return payments;
    }

    @Before
    public void initTest() {
        paymentsRepository.deleteAll();
        payments = createEntity();
    }

    @Test
    public void createPayments() throws Exception {
        int databaseSizeBeforeCreate = paymentsRepository.findAll().size();

        // Create the Payments
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);
        restPaymentsMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isCreated());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeCreate + 1);
        Payments testPayments = paymentsList.get(paymentsList.size() - 1);
        assertThat(testPayments.getRoom()).isEqualTo(DEFAULT_ROOM);
        assertThat(testPayments.getCustomer()).isEqualTo(DEFAULT_CUSTOMER);
        assertThat(testPayments.getDateOfJoin()).isEqualTo(DEFAULT_DATE_OF_JOIN);
        assertThat(testPayments.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPayments.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testPayments.getPaymentFrom()).isEqualTo(DEFAULT_PAYMENT_FROM);
        assertThat(testPayments.getPaymentTo()).isEqualTo(DEFAULT_PAYMENT_TO);
        assertThat(testPayments.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testPayments.getPayentAgainst()).isEqualTo(DEFAULT_PAYENT_AGAINST);
        assertThat(testPayments.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testPayments.getBuilding()).isEqualTo(DEFAULT_BUILDING);
    }

    @Test
    public void createPaymentsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentsRepository.findAll().size();

        // Create the Payments with an existing ID
        payments.setId("existing_id");
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentsMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkRoomIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsRepository.findAll().size();
        // set the field null
        payments.setRoom(null);

        // Create the Payments, which fails.
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        restPaymentsMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isBadRequest());

        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCustomerIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsRepository.findAll().size();
        // set the field null
        payments.setCustomer(null);

        // Create the Payments, which fails.
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        restPaymentsMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isBadRequest());

        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDateOfJoinIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsRepository.findAll().size();
        // set the field null
        payments.setDateOfJoin(null);

        // Create the Payments, which fails.
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        restPaymentsMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isBadRequest());

        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsRepository.findAll().size();
        // set the field null
        payments.setAmount(null);

        // Create the Payments, which fails.
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        restPaymentsMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isBadRequest());

        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPaymentDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsRepository.findAll().size();
        // set the field null
        payments.setPaymentDate(null);

        // Create the Payments, which fails.
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        restPaymentsMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isBadRequest());

        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPaymentFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsRepository.findAll().size();
        // set the field null
        payments.setPaymentFrom(null);

        // Create the Payments, which fails.
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        restPaymentsMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isBadRequest());

        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPaymentToIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsRepository.findAll().size();
        // set the field null
        payments.setPaymentTo(null);

        // Create the Payments, which fails.
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        restPaymentsMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isBadRequest());

        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPaymentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsRepository.findAll().size();
        // set the field null
        payments.setPaymentStatus(null);

        // Create the Payments, which fails.
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        restPaymentsMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isBadRequest());

        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPayentAgainstIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsRepository.findAll().size();
        // set the field null
        payments.setPayentAgainst(null);

        // Create the Payments, which fails.
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        restPaymentsMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isBadRequest());

        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkBuildingIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsRepository.findAll().size();
        // set the field null
        payments.setBuilding(null);

        // Create the Payments, which fails.
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        restPaymentsMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isBadRequest());

        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllPayments() throws Exception {
        // Initialize the database
        paymentsRepository.save(payments);

        // Get all the paymentsList
        restPaymentsMockMvc.perform(get("/api/payments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payments.getId())))
            .andExpect(jsonPath("$.[*].room").value(hasItem(DEFAULT_ROOM.toString())))
            .andExpect(jsonPath("$.[*].customer").value(hasItem(DEFAULT_CUSTOMER.toString())))
            .andExpect(jsonPath("$.[*].dateOfJoin").value(hasItem(DEFAULT_DATE_OF_JOIN.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentFrom").value(hasItem(DEFAULT_PAYMENT_FROM.toString())))
            .andExpect(jsonPath("$.[*].paymentTo").value(hasItem(DEFAULT_PAYMENT_TO.toString())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].payentAgainst").value(hasItem(DEFAULT_PAYENT_AGAINST.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].building").value(hasItem(DEFAULT_BUILDING.toString())));
    }

    @Test
    public void getPayments() throws Exception {
        // Initialize the database
        paymentsRepository.save(payments);

        // Get the payments
        restPaymentsMockMvc.perform(get("/api/payments/{id}", payments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(payments.getId()))
            .andExpect(jsonPath("$.room").value(DEFAULT_ROOM.toString()))
            .andExpect(jsonPath("$.customer").value(DEFAULT_CUSTOMER.toString()))
            .andExpect(jsonPath("$.dateOfJoin").value(DEFAULT_DATE_OF_JOIN.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.paymentDate").value(DEFAULT_PAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.paymentFrom").value(DEFAULT_PAYMENT_FROM.toString()))
            .andExpect(jsonPath("$.paymentTo").value(DEFAULT_PAYMENT_TO.toString()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()))
            .andExpect(jsonPath("$.payentAgainst").value(DEFAULT_PAYENT_AGAINST.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.building").value(DEFAULT_BUILDING.toString()));
    }

    @Test
    public void getNonExistingPayments() throws Exception {
        // Get the payments
        restPaymentsMockMvc.perform(get("/api/payments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePayments() throws Exception {
        // Initialize the database
        paymentsRepository.save(payments);
        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();

        // Update the payments
        Payments updatedPayments = paymentsRepository.findOne(payments.getId());
        updatedPayments
            .room(UPDATED_ROOM)
            .customer(UPDATED_CUSTOMER)
            .dateOfJoin(UPDATED_DATE_OF_JOIN)
            .amount(UPDATED_AMOUNT)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .paymentFrom(UPDATED_PAYMENT_FROM)
            .paymentTo(UPDATED_PAYMENT_TO)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .payentAgainst(UPDATED_PAYENT_AGAINST)
            .comments(UPDATED_COMMENTS)
            .building(UPDATED_BUILDING);
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(updatedPayments);

        restPaymentsMockMvc.perform(put("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isOk());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
        Payments testPayments = paymentsList.get(paymentsList.size() - 1);
        assertThat(testPayments.getRoom()).isEqualTo(UPDATED_ROOM);
        assertThat(testPayments.getCustomer()).isEqualTo(UPDATED_CUSTOMER);
        assertThat(testPayments.getDateOfJoin()).isEqualTo(UPDATED_DATE_OF_JOIN);
        assertThat(testPayments.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPayments.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testPayments.getPaymentFrom()).isEqualTo(UPDATED_PAYMENT_FROM);
        assertThat(testPayments.getPaymentTo()).isEqualTo(UPDATED_PAYMENT_TO);
        assertThat(testPayments.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testPayments.getPayentAgainst()).isEqualTo(UPDATED_PAYENT_AGAINST);
        assertThat(testPayments.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testPayments.getBuilding()).isEqualTo(UPDATED_BUILDING);
    }

    @Test
    public void updateNonExistingPayments() throws Exception {
        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();

        // Create the Payments
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPaymentsMockMvc.perform(put("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isCreated());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deletePayments() throws Exception {
        // Initialize the database
        paymentsRepository.save(payments);
        int databaseSizeBeforeDelete = paymentsRepository.findAll().size();

        // Get the payments
        restPaymentsMockMvc.perform(delete("/api/payments/{id}", payments.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Payments.class);
        Payments payments1 = new Payments();
        payments1.setId("id1");
        Payments payments2 = new Payments();
        payments2.setId(payments1.getId());
        assertThat(payments1).isEqualTo(payments2);
        payments2.setId("id2");
        assertThat(payments1).isNotEqualTo(payments2);
        payments1.setId(null);
        assertThat(payments1).isNotEqualTo(payments2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentsDTO.class);
        PaymentsDTO paymentsDTO1 = new PaymentsDTO();
        paymentsDTO1.setId("id1");
        PaymentsDTO paymentsDTO2 = new PaymentsDTO();
        assertThat(paymentsDTO1).isNotEqualTo(paymentsDTO2);
        paymentsDTO2.setId(paymentsDTO1.getId());
        assertThat(paymentsDTO1).isEqualTo(paymentsDTO2);
        paymentsDTO2.setId("id2");
        assertThat(paymentsDTO1).isNotEqualTo(paymentsDTO2);
        paymentsDTO1.setId(null);
        assertThat(paymentsDTO1).isNotEqualTo(paymentsDTO2);
    }
}
