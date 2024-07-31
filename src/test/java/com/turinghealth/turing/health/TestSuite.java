package com.turinghealth.turing.health;

import com.turinghealth.turing.health.service.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        HospitalServiceTest.class,
        RegionServiceTest.class,
        UserServiceTest.class,
        ProductServiceTest.class,
        CategoryServiceTest.class,
        AuthenticationServiceTest.class,
        AddressUserServiceTest.class,
        ConsultationServiceTest.class,
        TransactionServiceTest.class,
        MidTransServiceTest.class,
        OrderItemServiceTest.class,
        ProfileServiceTest.class
})
public class TestSuite {
}
