package com.externo.servico;

import com.externo.dto.EmailDTO;
import com.externo.model.Email;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.*;
//TODO na teoria pronto, mas falta testar
@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @Test
    void testEnviarEmail() {
        // Given
        EmailDTO emailDTO = new EmailDTO("recipient@example.com", "Subject", "Body");

        // When
        Email result = emailService.enviarEmail(emailDTO);

        // Then
        assertEquals(result.getId(), new Email(emailDTO).getId());

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(messageCaptor.capture());

        SimpleMailMessage capturedMessage = messageCaptor.getValue();
        assertEquals(emailDTO.email(), capturedMessage.getTo()[0]);
        assertEquals(emailDTO.assunto(), capturedMessage.getSubject());
        assertEquals(emailDTO.mensagem(), capturedMessage.getText());
    }
}
