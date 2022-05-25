package gs.shiptrackingeventsourcing.api;

import gs.shiptrackingeventsourcing.model.Location;
import gs.shiptrackingeventsourcing.service.LocationManagementService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LocationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LocationManagementService locationManagementService;

    @InjectMocks
    private LocationController controller;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    public void addLocation_withNoRequestBody_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/location/add")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(locationManagementService);
    }

    @Test
    public void addLocation_withEmptyRequestBody_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/location/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(locationManagementService);
    }

    @Test
    public void addLocation_withInvalidRequestBody_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/location/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"foo\": \"bar\"}"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(locationManagementService);
    }

    @Test
    public void addLocation_withValidRequestBody_returnsSuccess() throws Exception {
        mockMvc.perform(post("/api/location/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"shipId\": \"TLB01\", \"shipLocation\": \"home of the penguins\"}"))
                .andExpect(status().isCreated());

        verify(locationManagementService).updateShipLocation(Location.builder().shipId("TLB01").shipLocation("home of the penguins").build());
    }

    @Test
    public void listLocations_returnsSuccess() throws Exception {
        when(locationManagementService.getCurrentLocations()).thenReturn(List.of(Location.builder().shipId("Foo").shipLocation("Foobar").build()));

        MvcResult result = mockMvc.perform(get("/api/location/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals("[{\"shipId\":\"Foo\",\"shipLocation\":\"Foobar\"}]", result.getResponse().getContentAsString());

        verify(locationManagementService).getCurrentLocations();
    }
}