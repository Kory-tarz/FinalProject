package pl.cyryl.finalproject.users.user;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.cyryl.finalproject.util.SessionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    private static final long TEST_USER_ID = 1;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private SessionService sessionService;


    private User Kowalski() {
        User kowalski = new User();
        kowalski.setId(TEST_USER_ID);
        kowalski.setFirstName("Jan");
        kowalski.setLastName("Kowalski");
        kowalski.setEmail("jan.kowalski@mail.com");
        kowalski.setPassword("12345678");
        kowalski.setUsername("Jan22");
        return kowalski;
    }

    @BeforeEach
    void setup() {
        User kowalski = Kowalski();
        given(this.userRepository.findByEmail(eq(kowalski.getEmail()))).willReturn(Optional.of(kowalski));
        given(this.userRepository.findById(eq(TEST_USER_ID))).willReturn(Optional.of(kowalski));
        given(this.sessionService.getCurrentUserId(any())).willReturn(TEST_USER_ID);
    }

    @WithMockUser(roles = "USER")
    @Test
    void testInitUserDetails() throws Exception {
        mockMvc.perform(get("/user/details/{id}", TEST_USER_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user", "dirName"))
                .andExpect(view().name("user/details"));
    }

    @WithMockUser(roles = "USER")
    @Test
    void testInitEditForm() throws Exception {
        User kowalski = Kowalski();
        mockMvc.perform(get("/user/edit/{id}", TEST_USER_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user", "pictureDir", "profile_pictures"))
                .andExpect(model().attribute("user", hasProperty("username", is(kowalski.getUsername()))))
                .andExpect(model().attribute("user", hasProperty("id", is(TEST_USER_ID))))
                .andExpect(model().attribute("user", hasProperty("firstName", is(kowalski.getFirstName()))))
                .andExpect(model().attribute("user", hasProperty("lastName", is(kowalski.getLastName()))))
                .andExpect(model().attribute("user", hasProperty("email", is(kowalski.getEmail()))))
                .andExpect(view().name("user/edit"));
    }

    @WithMockUser(roles = "USER")
    @Test
    void testProcessEditUserSuccess() throws Exception {
        mockMvc.perform(post("/user/edit/{id}", TEST_USER_ID)
                        .param("email", "jan.kowalski@mail.com")
                        .param("profilePhotoFile", "3")
                        .param("password", "12345678")
                        .param("username", "JanTest")
                        .param("firstName", "Janusz")
                        .param("lastName", "Kowalski2")
                        .param("address", "Polska")
                        .param("about", "o mnie")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/details/" + TEST_USER_ID));
    }

    @WithMockUser(roles = "USER")
    @Test
    void testProcessEditUserErrors() throws Exception {
        mockMvc.perform(post("/user/edit/{id}", TEST_USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("email", "jan.kowalski@mail.com")
                        .param("username", "")
                        .param("firstName", "")
                        .param("lastName", "")
                        .param("address", "Polska")
                        .param("about", "o mnie")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("user"))
                .andExpect(model().attributeHasFieldErrors("user", "username"))
                .andExpect(model().attributeHasFieldErrors("user", "firstName"))
                .andExpect(model().attributeHasFieldErrors("user", "lastName"))
                .andExpect(view().name("user/edit"));
    }

    @WithMockUser(roles = "USER")
    @Test
    void testInitUserPanel() throws Exception {
        User kowalski = Kowalski();
        mockMvc.perform(get("/user/panel"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", hasProperty("username", is(kowalski.getUsername()))))
                .andExpect(model().attribute("user", hasProperty("id", is(TEST_USER_ID))))
                .andExpect(model().attribute("user", hasProperty("firstName", is(kowalski.getFirstName()))))
                .andExpect(model().attribute("user", hasProperty("lastName", is(kowalski.getLastName()))))
                .andExpect(model().attribute("user", hasProperty("email", is(kowalski.getEmail()))))
                .andExpect(view().name("user/panel"));
    }
}