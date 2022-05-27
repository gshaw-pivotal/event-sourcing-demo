package gs.shiptrackingeventsourcing.api;

import gs.shiptrackingeventsourcing.model.Ship;
import gs.shiptrackingeventsourcing.model.Status;
import gs.shiptrackingeventsourcing.model.StatusType;
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
        mockMvc.perform(post("/api/ship/add")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(shipManagementService);
    }

    @Test
    public void addShip_withEmptyRequestBody_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/ship/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(shipManagementService);
    }

    @Test
    public void addShip_withInvalidRequestBody_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/ship/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"foo\": \"bar\"}"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(shipManagementService);
    }

    @Test
    public void addShip_withValidRequestBody_returnsSuccess() throws Exception {
        mockMvc.perform(post("/api/ship/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"TLB01\", \"name\": \"this little boat\"}"))
                .andExpect(status().isCreated());

        verify(shipManagementService).addShip(Ship.builder().id("TLB01").name("this little boat").build());
    }

    @Test
    public void removeShip_withNoId_returnsNotFound() throws Exception {
        mockMvc.perform(delete("/api/ship/remove")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        mockMvc.perform(delete("/api/ship/remove/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        verifyNoInteractions(shipManagementService);
    }

    @Test
    public void removeShip_withId_returnsSuccess() throws Exception {
        mockMvc.perform(delete("/api/ship/remove/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(shipManagementService).removeShip("1");
    }

    @Test
    public void listShips_returnsSuccess() throws Exception {
        when(shipManagementService.getShipList()).thenReturn(List.of(Ship.builder().id("Foo").name("Foobar").build()));

        MvcResult result = mockMvc.perform(get("/api/ship/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals("[{\"id\":\"Foo\",\"name\":\"Foobar\"}]", result.getResponse().getContentAsString());

        verify(shipManagementService).getShipList();
    }

    @Test
    public void getShip_returnsSuccess() throws Exception {
        when(shipManagementService.getShip("Foo")).thenReturn(Ship.builder().id("Foo").name("Foobar").build());

        MvcResult result = mockMvc.perform(get("/api/ship/Foo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals("{\"id\":\"Foo\",\"name\":\"Foobar\"}", result.getResponse().getContentAsString());

        verify(shipManagementService).getShip("Foo");
    }

    @Test
    public void updateStatus_withNoRequestBody_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/ship/status")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(shipManagementService);
    }

    @Test
    public void updateStatus_withEmptyRequestBody_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/ship/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(shipManagementService);
    }

    @Test
    public void updateStatus_withInvalidRequestBody_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/ship/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"foo\": \"bar\"}"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/api/ship/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"shipId\": \"TLB01\", \"shipStatus\": \"NOTVALID\"}"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(shipManagementService);
    }

    @Test
    public void updateStatus_withValidRequestBody_returnsSuccess() throws Exception {
        mockMvc.perform(post("/api/ship/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"shipId\": \"TLB01\", \"shipStatus\": \"UNDERWAY\"}"))
                .andExpect(status().isOk());

        verify(shipManagementService).updateShipStatus(Status.builder().shipId("TLB01").shipStatus(StatusType.UNDERWAY).build());
    }

    @Test
    public void listShipStatus_returnsSuccess() throws Exception {
        when(shipManagementService.getShipStatusList()).thenReturn(List.of(Status.builder().shipId("Foo").shipStatus(StatusType.UNDERWAY).build()));

        MvcResult result = mockMvc.perform(get("/api/ship/status/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals("[{\"shipId\":\"Foo\",\"shipStatus\":\"UNDERWAY\"}]", result.getResponse().getContentAsString());

        verify(shipManagementService).getShipStatusList();
    }

    @Test
    public void getShipStatus_forAShipThatExists_returnsSuccess() throws Exception {
        when(shipManagementService.getShipStatus("1")).thenReturn(Status.builder().shipId("Foo").shipStatus(StatusType.UNDERWAY).build());

        MvcResult result = mockMvc.perform(get("/api/ship/1/status")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals("{\"shipId\":\"Foo\",\"shipStatus\":\"UNDERWAY\"}", result.getResponse().getContentAsString());

        verify(shipManagementService).getShipStatus("1");
    }

    @Test
    public void getShipStatus_forAShipThatDoesNotExists_returnsSuccess() throws Exception {
        when(shipManagementService.getShipStatus(anyString())).thenReturn(null);

        MvcResult result = mockMvc.perform(get("/api/ship/1/status")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals("", result.getResponse().getContentAsString());

        verify(shipManagementService).getShipStatus("1");
    }
}