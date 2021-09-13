package kacper.klimczuk.usersservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import kacper.klimczuk.usersservice.exceptions.UserNotFoundException;
import kacper.klimczuk.usersservice.models.dto.UserDTO;
import kacper.klimczuk.usersservice.services.StatsService;
import kacper.klimczuk.usersservice.services.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    private static final String LOGIN = "testLogin";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private StatsService statsService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private static UserDTO USER_DTO;

    @BeforeAll
    private static void init() {
        USER_DTO = new UserDTO();
        USER_DTO.setLogin(LOGIN);
    }

    @Test
    public void shouldReturnUserDataForValidLogin() throws Exception {
        when(userService.getUserInfo(LOGIN)).thenReturn(USER_DTO);
        MvcResult result = mockMvc.perform(get("/api/users/" + LOGIN))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        assertEquals(content, objectMapper.writeValueAsString(USER_DTO));
    }

    @Test
    public void shouldThrowExceptionForInvalidLogin() throws Exception {
        when(userService.getUserInfo(LOGIN)).thenThrow(new UserNotFoundException(LOGIN));
        mockMvc.perform(get("/api/users/" + LOGIN))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException));
    }
}