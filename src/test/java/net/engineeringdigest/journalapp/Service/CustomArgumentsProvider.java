package net.engineeringdigest.journalapp.Service;

import net.engineeringdigest.journalapp.Entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class CustomArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
                Arguments.of(User.builder().userName("spring").password("akhil123").roles(List.of("USER")).build()),
                Arguments.of(User.builder().userName("boot").password("ayush123").roles(List.of("USER")).build())
        );
    }

}
