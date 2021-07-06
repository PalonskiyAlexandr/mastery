package com.palonskiy.converters;

import com.palonskiy.dto.AuthorDto;
import com.palonskiy.model.Author;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorConverter {

    public List<AuthorDto> toDtoList(List<Author> authors) {
        return authors.stream()
                .map(this::toAuthorDto)
                .collect(Collectors.toList());
    }

    public List<Author> toList(List<AuthorDto> authorsDto) {
        return authorsDto.stream()
                .map(this::toAuthor)
                .collect(Collectors.toList());
    }

    public Author toAuthor(AuthorDto authorDto) {
        Author author = new Author();
        BeanUtils.copyProperties(authorDto, author);
        return author;
    }

    public AuthorDto toAuthorDto(Author author) {
        AuthorDto authorDto = new AuthorDto();
        BeanUtils.copyProperties(author, authorDto);
        return authorDto;
    }
}
