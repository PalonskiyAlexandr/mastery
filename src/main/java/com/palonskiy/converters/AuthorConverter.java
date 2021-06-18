package com.palonskiy.converters;

import com.palonskiy.dto.AuthorDto;
import com.palonskiy.model.Author;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthorConverter {

    public List<AuthorDto> toDtoList(List<Author> authors) {
        List<AuthorDto> authorsDto = new ArrayList<>();
        /*AuthorDto authorDto = new AuthorDto();
        authors.stream()
                .peek(author -> authorsDto.add(new AuthorDto()))
                .forEach(author -> BeanUtils.copyProperties(author, authorDto));*/
        for (Author author : authors) {
            AuthorDto authorDto = new AuthorDto();
            BeanUtils.copyProperties(author, authorDto);
            authorsDto.add(authorDto);
        }
        return authorsDto;
    }

    public List<Author> toList(List<AuthorDto> authorsDto) {
        List<Author> authors = new ArrayList<>();
        for (AuthorDto authorDto : authorsDto) {
            Author author = new Author();
            BeanUtils.copyProperties(authorDto, author);
            authors.add(author);
        }
        return authors;
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
