package gs.shiptrackingeventsourcing.api;

import gs.shiptrackingeventsourcing.model.Ship;
import gs.shiptrackingeventsourcing.service.ShipManagementService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ShipControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ShipManagementService shipManagementService;

    @InjectMocks
    private ShipController controller;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    public void addShip_withNoRequestBody_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/add")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(shipManagementService);
    }

    @Test
    public void addShip_withEmptyRequestBody_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(shipManagementService);
    }

    @Test
    public void addShip_withInvalidRequestBody_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"foo\": \"bar\"}"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(shipManagementService);
    }

    @Test
    public void addShip_withValidRequestBody_returnsSuccess() throws Exception {
        mockMvc.perform(post("/api/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"TLB01\", \"name\": \"this little boat\"}"))
                .andExpect(status().isCreated());

        verify(shipManagementService).addShip(Ship.builder().id("TLB01").name("this little boat").build());
    }

    @Test
    public void removeShip_withNoId_returnsNotFound() throws Exception {
        mockMvc.perform(delete("/api/remove")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/api/remove/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verifyNoInteractions(shipManagementService);
    }

    @Test
    public void removeShip_withId_returnsSuccess() throws Exception {
        mockMvc.perform(delete("/api/remove/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(shipManagementService).removeShip("1");
    }

    @Test
    public void listShips_returnsSuccess() throws Exception {
        when(shipManagementService.getShipList()).thenReturn(List.of(Ship.builder().id("Foo").name("Foobar").build()));

        MvcResult result = mockMvc.perform(get("/api/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals("[{\"id\":\"Foo\",\"name\":\"Foobar\"}]", result.getResponse().getContentAsString());

        verify(shipManagementService).getShipList();
    }
}