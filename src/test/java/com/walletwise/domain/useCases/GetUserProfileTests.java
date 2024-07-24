package com.walletwise.domain.useCases;

import com.walletwise.domain.adapters.IAuthAdapter;
import com.walletwise.domain.entities.exceptions.ForbiddenException;
import com.walletwise.domain.entities.models.Profile;
import com.walletwise.mocks.Mocks;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class GetUserProfileTests {
    private GetUserProfile getUserProfile;

    @MockBean
    private IAuthAdapter authAdapter;

    @BeforeEach
    void setup() {
        this.getUserProfile = new GetUserProfile(authAdapter);
    }

    @Test
    @DisplayName("Should return ForbiddenException if authAdapter returns null")
    void shouldReturnForbiddenExceptionIfAuthAdapterReturnsNull() {
        Mockito.when(authAdapter.getUserProfile()).thenReturn(null);

        Throwable exception = Assertions.catchThrowable(() -> this.getUserProfile.getUserProfile());
        Assertions.assertThat(exception).isInstanceOf(ForbiddenException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Forbidden.");
    }

    @Test
    @DisplayName("Should return user profile")
    void shouldUserProfile() {
        Profile profile = Mocks.profileFactory();
        Mockito.when(this.authAdapter.getUserProfile()).thenReturn(profile);

        Profile result = this.getUserProfile.getUserProfile();

        Assertions.assertThat(result).isEqualTo(profile);
        Mockito.verify(this.authAdapter).getUserProfile();
    }
}
