package gs.shiptrackingeventsourcing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShipTrackingEventSourcingApplicationTests {

	@LocalServerPort
	private int serverPort;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
		ResponseEntity response = restTemplate.getForEntity("http://localhost:" + serverPort + "/api/list", List.class);

		Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
	}
}
