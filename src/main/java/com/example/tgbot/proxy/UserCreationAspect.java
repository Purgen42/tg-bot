package com.example.tgbot.proxy;

import com.example.tgbot.entity.user.Action;
import com.example.tgbot.entity.user.Role;
import com.example.tgbot.entity.user.UserDetails;
import com.example.tgbot.repository.DetailsRepository;
import com.example.tgbot.repository.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.example.tgbot.entity.user.User;

import java.time.LocalDateTime;

@Aspect
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationAspect {
    final UserRepository userRepository;
    final DetailsRepository detailsRepository;

    @Autowired
    public UserCreationAspect(UserRepository userRepository, DetailsRepository detailsRepository) {
        this.userRepository = userRepository;
        this.detailsRepository = detailsRepository;
    }

    @Pointcut("execution(* com.example.tgbot.service.UpdateDispatcher.distribute(..))")
    public void distributeMethodPointcut() {}

    @Around("distributeMethodPointcut()")
    public Object distributeMethodAvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Object args[] = joinPoint.getArgs();
        Update update = (Update) args[0];
        org.telegram.telegrambots.meta.api.objects.User telegramUser;

        if (update.hasMessage()) {
            telegramUser = update.getMessage().getFrom();
        } else if (update.hasCallbackQuery()) {
            telegramUser = update.getCallbackQuery().getFrom();
        } else {
            return joinPoint.proceed();
        }

        if (userRepository.existsById(telegramUser.getId())) {
            return joinPoint.proceed();
        }

        UserDetails details = UserDetails.builder()
                .username(telegramUser.getUserName())
                .firstName(telegramUser.getFirstName())
                .lastName(telegramUser.getLastName())
                .registeredAt(LocalDateTime.now())
                .build();

        detailsRepository.save(details);

        User newUser = User.builder()
                .chatId(telegramUser.getId())
                .action(Action.FREE)
                .role(Role.USER)
                .details(details)
                .build();

        userRepository.save(newUser);

        return joinPoint.proceed();
    }
}
