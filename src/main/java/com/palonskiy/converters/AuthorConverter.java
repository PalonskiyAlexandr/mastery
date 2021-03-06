package com.palonskiy.converters;

import com.palonskiy.dto.AuthorDto;
import com.palonskiy.model.Author;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class AuthorConverter {
    public static List<AuthorDto> toDtoList(List<Author> authors) {
        List<AuthorDto> authorsDto = new ArrayList<>();
        for (Author author : authors) {
            AuthorDto authorDto = new AuthorDto();
            BeanUtils.copyProperties(author, authorDto);
            authorsDto.add(authorDto);
        }
        return authorsDto;
    }

    public static Author toAuthor(AuthorDto authorDto) {
        Author author = new Author();
        BeanUtils.copyProperties(authorDto, author);
        return author;
    }

    public static AuthorDto toAuthorDto(Author author) {
        AuthorDto authorDto = new AuthorDto();
        BeanUtils.copyProperties(author, authorDto);
        return authorDto;
    }
}
