package rest.student1.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import rest.domain.Price;
import rest.repository.PriceRepository;

import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")  // Postavljanje profila na "test"
public class PriceRepositoryTest {

    @Autowired
    private PriceRepository priceRepository;

    private Date start;
    private Date endDate;
    private double amount;
    @Test
    public void shouldSavePrice(){
        Date mockStartDate = new Date();
        Date mockEndDate = new Date(mockStartDate.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mockEndDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        mockEndDate = calendar.getTime();

        Price price = new Price(mockStartDate,mockEndDate,100) ;
        Price savedUser = priceRepository.save(price);

        assertThat(savedUser).usingRecursiveComparison().ignoringFields("userId").isEqualTo(price);

    }
}
