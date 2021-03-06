package com.github.oxaoo.qas.boundary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.oxaoo.mp4ru.exceptions.FailedConllMapException;
import com.github.oxaoo.mp4ru.exceptions.FailedParsingException;
import com.github.oxaoo.qas.core.QasEngine;
import com.github.oxaoo.qas.exceptions.CreateAnswerException;
import com.github.oxaoo.qas.exceptions.FailedQuestionTokenMapException;
import com.github.oxaoo.qas.exceptions.InitQasEngineException;
import com.github.oxaoo.qas.exceptions.LoadQuestionClassifierModelException;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 * @author Alexander Kuleshov
 * @version 1.0
 * @since 01.04.2017
 */
@ApplicationPath("/qas")
@Path("/ask")
public class QasService extends Application {
    private final QasEngine qasEngine;

    public QasService() throws LoadQuestionClassifierModelException, InitQasEngineException {
        super();
        this.qasEngine = new QasEngine();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response ask(@QueryParam("question") @NotNull @Size(min = 4) String question)
            throws FailedParsingException,
            FailedConllMapException,
            FailedQuestionTokenMapException,
            JsonProcessingException {
        Set<String> answers = null;
        try {
            answers = this.qasEngine.answer(question);
            ObjectMapper objectMapper = new ObjectMapper();
            String str = objectMapper.writeValueAsString(answers);
            return Response.ok(str).build();
        } catch (CreateAnswerException e) {
            //todo add message
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @Override
    public Set<Class<?>> getClasses() {
        return new HashSet<>(Collections.singletonList(QasService.class));
    }
}
