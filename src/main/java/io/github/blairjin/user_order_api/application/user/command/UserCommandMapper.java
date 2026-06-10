package io.github.blairjin.user_order_api.application.user.command;

import io.github.blairjin.user_order_api.domain.user.vo.*;
import io.github.blairjin.user_order_api.dto.user.UserCreateRequest;
import io.github.blairjin.user_order_api.dto.user.UserProfileUpdateRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.Objects;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserCommandMapper {

    public static UserCreateCommand toCommand(LoginId loginId, EncodedPassword encodedPassword, String email) {
        return new UserCreateCommand(
                loginId,
                encodedPassword,
                Email.of(email)
        );
    }

    public static UserProfileCreateCommand toCommand(UserCreateRequest request) {
        return new UserProfileCreateCommand(
                request.userName()!=null ? UserName.of(request.userName()) : null,
                request.firstName(),
                request.lastName(),
                request.birthDate()!=null ? BirthDate.of(request.birthDate()) : null,
                request.phoneNumber()!=null ? PhoneNumber.of(request.phoneNumber()) : null
        );
    }

    public static UserProfileUpdateCommand toUpdateCommand(UserProfileUpdateRequest request){
        return new UserProfileUpdateCommand(
                map(request.userName(), UserName::of),
                request.firstName(),
                request.lastName(),
                map(request.birthDate(), BirthDate::of),
                map(request.phoneNumber(), PhoneNumber::of)
        );
    }

    private static <T, R> JsonNullable<R> map(
            JsonNullable<T> source,
            Function<T, R> converter
    ){
        Objects.requireNonNull(source);

        if(JsonNullable.undefined().equals(source)){
            return JsonNullable.undefined();
        }

        T value = source.orElse(null);

        if(value==null){
            return JsonNullable.of(null);
        }

        return JsonNullable.of(converter.apply(value));
    }
}