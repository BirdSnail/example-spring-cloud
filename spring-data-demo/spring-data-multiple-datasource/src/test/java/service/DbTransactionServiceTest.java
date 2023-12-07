package service;

import com.birdsnail.multiple.base.City;
import com.birdsnail.multiple.base.EcLabel;
import com.birdsnail.multiple.service.DbService;
import com.birdsnail.multiple.service.DbTransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class DbTransactionServiceTest {

    @Autowired
    DbTransactionService dbTransactionService;

    @Autowired
    DbService dbService;

    @Test
    void testDsTransaction() {
    }

    @Test
    void testTransactionWithAnnotation() {
    }

    @Test
    void testTransactionWithPropagationNew() {
    }

    @Test
    void testTransactionBySeataAt() {
        City city = dbService.testDynamicLocalQuery(2);
        EcLabel ecLabel = dbService.testDynamicDevQuery(112L);

        assertThrows(RuntimeException.class, () -> dbTransactionService.testTransactionBySeataAt(2, 112L));

        City afterCity = dbService.testDynamicLocalQuery(2);
        EcLabel afterEcLabel = dbService.testDynamicDevQuery(112L);
        assertEquals(city, afterCity);
        assertEquals(ecLabel, afterEcLabel);
    }
}