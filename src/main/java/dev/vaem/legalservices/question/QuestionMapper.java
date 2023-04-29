package dev.vaem.legalservices.question;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import dev.vaem.legalservices.question.bytags.QuestionByTag;
import dev.vaem.legalservices.question.byuser.QuestionByUser;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuestionMapper {

    default List<QuestionByTag> toQuestionsByTag(Question question) {
        return question.getTags().stream()
                .map(tag -> QuestionByTag.builder()
                        .tag(tag)
                        .questionId(question.getQuestionId())
                        .userEmail(question.getUserEmail())
                        .header(question.getHeader())
                        .build())
                .toList();
    }

    QuestionByUser toQuestionByUser(Question question);

}
