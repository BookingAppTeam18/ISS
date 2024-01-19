package rest.student1.service;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rest.repository.PriceRepository;

@ExtendWith(MockitoExtension.class)
public class PriceServiceTest {

    @Mock
    private PriceRepository priceRepository;
}
