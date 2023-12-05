package com.basirhat.questionaries.controller;


import com.basirhat.questionaries.service.QuestionService;
import com.basirhat.questionnaires.model.Question;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

    @Captor
    private ArgumentCaptor<List<Question>> argumentCaptor;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void shouldPostAndReturn202WhenControllerIsCalledSuccessfully() throws Exception {
        String question = """
                {
                  "type": "java 17",
                  "question": "Which of the following methods compile? (Choose all that apply.)",
                  "options": [
                    {
                      "optionId": "A",
                      "description": "public void january(){return;}",
                      "sequence": 1
                    },
                    {
                      "optionId": "B",
                      "description": "public int february() { return null;}",
                      "sequence": 2
                    },
                    {
                      "optionId": "C",
                      "description": "public void march() {}",
                      "sequence": 3
                    },
                    {
                      "optionId": "D",
                      "description": "public int april() { return 9;}",
                      "sequence": 4
                    },
                    {
                      "optionId": "E",
                      "description": "public int may() { return 9.0;}",
                      "sequence": 5
                    },
                    {
                      "optionId": "F",
                      "description": "public int june() { return;}",
                      "sequence": 6
                    }
                  ],
                  "answers" : ["A","B"]
                }
                """;

        mockMvc.perform(
                        post("/v1/question")
                                .contentType(APPLICATION_JSON_VALUE)
                                .content(question))
                .andExpect(status().isAccepted())
                .andReturn();

        verify(questionService).saveQuestions(argumentCaptor.capture());
//TODO : write all assert
        assertEquals(1, argumentCaptor.getValue().size());
        assertEquals("Which of the following methods compile? (Choose all that apply.)", argumentCaptor.getValue().get(0).question());
        assertEquals("java 17", argumentCaptor.getValue().get(0).type());
        assertEquals(Arrays.asList("A", "B"), argumentCaptor.getValue().get(0).answers());

        assertEquals(6, argumentCaptor.getValue().get(0).options().size());

        assertEquals("public void january(){return;}", argumentCaptor.getValue().get(0).options().get(0).description());
        assertEquals("A", argumentCaptor.getValue().get(0).options().get(0).optionId());
        assertEquals(1, argumentCaptor.getValue().get(0).options().get(0).sequence());

        //question
        //type
        //answer
        //option size
        //option - optionid
        //option - description
        //sequence

    }

    @Test
    void shouldPostAndReturn400WhenEmptyQuestionIsSent() throws Exception {
        String question = """
                {
                  "type": "java 17",
                  "question": "",
                  "options": [
                    {
                      "optionId": "A",
                      "description": "public void january() { return; }",
                      "sequence": 1
                    },
                    {
                      "optionId": "B",
                      "description": "public int february() { return null;}",
                      "sequence": 2
                    },
                    {
                      "optionId": "C",
                      "description": "public void march() {}",
                      "sequence": 3
                    },
                    {
                      "optionId": "D",
                      "description": "public int april() { return 9;}",
                      "sequence": 4
                    },
                    {
                      "optionId": "E",
                      "description": "public int may() { return 9.0;}",
                      "sequence": 5
                    },
                    {
                      "optionId": "F",
                      "description": "public int june() { return;}",
                      "sequence": 6
                    }
                  ],
                  "answers" :["A","B"]
                   }
                """;

        mockMvc.perform(
                        post("/v1/question")
                                .contentType(APPLICATION_JSON_VALUE)
                                .content(question))
                .andExpect(status().isBadRequest())
                .andReturn();

        verify(questionService, never()).saveQuestions(argumentCaptor.capture());
    }

    @Test
    void shouldReturn400WhenQuestionIsNull() throws Exception {
        String question = """
                               
              {
                  "type": "java 17",
                  "options": [
                    {
                      "optionId": "A",
                      "description": "public void january() { return; }",
                      "sequence": 1
                    },
                    {
                      "optionId": "B",
                      "description": "public int february() { return null;}",
                      "sequence": 2
                    }
                  ],
                  "answers": [
                    "A",
                    "B"
                  ]
                }
                                
                """;
        mockMvc.perform(
                        post("/v1/question")
                                .contentType(APPLICATION_JSON_VALUE)
                                .content(question))
                .andExpect(status().isBadRequest())
                .andReturn();
        verify(questionService, never()).saveQuestions(argumentCaptor.capture());
    }

    @Test
    void shouldReturn400WhenQuestionIsEmpty() throws Exception {
        String question = """
                                
                  {
                    "questionId": 1,
                    "type": "java 17",
                    "question": "",
                    "options": [
                      {
                        "optionId": "A",
                        "description": "public void january() { return; }",
                        "sequence": 1
                      },
                      {
                        "optionId": "B",
                        "description": "public int february() { return null;}",
                        "sequence": 2
                      }
                    ],
                    "answers": [
                      "A",
                      "B"
                    ]
                  }
                                
                """;
        mockMvc.perform(
                        post("/v1/question")
                                .contentType(APPLICATION_JSON_VALUE)
                                .content(question))
                .andExpect(status().isBadRequest())
                .andReturn();
        verify(questionService, never()).saveQuestions(argumentCaptor.capture());
    }

    @Test
    void shouldReturn400WhenTypeIsNull() throws Exception {
        //TODO - question
        String question = """
                                
                  {
                    "questionId": 1,
                    "question": "Which statements about the following class are true? (Choose all that apply.)",
                    "options": [
                      {
                        "optionId": "A",
                        "description": "public void january() { return; }",
                        "sequence": 1
                      },
                      {
                        "optionId": "B",
                        "description": "public int february() { return null;}",
                        "sequence": 2
                      }
                    ],
                    "answers": ["A",
                      "B"
                    ]
                  }
                                
                """;
        MvcResult mvcResult = mockMvc.perform(
                        post("/v1/question")
                                .contentType(APPLICATION_JSON_VALUE)
                                .content(question))
                .andExpect(status().isBadRequest())
                .andReturn();

        verify(questionService, never()).saveQuestions(argumentCaptor.capture());

        ProblemDetail problemDetail = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProblemDetail.class);

        LinkedHashMap<String, Object> errors = (LinkedHashMap<String, Object>) problemDetail.getProperties();

        List<LinkedHashMap<String, Object>> list = (List<LinkedHashMap<String, Object>>) errors.get("errors");

        assertEquals("Question type cannot be blank", list.get(0).get("message"));
    }

    @Test
    void shouldReturn400WhenTypeIsEmpty() throws Exception {
        String question = """
                                
                  {
                    "questionId": 1,
                    "type": "",
                    "question": "Which statements about the following class are true? (Choose all that apply.)",
                    "options": [
                      {
                        "optionId": "A",
                        "description": "public void january() { return; }",
                        "sequence": 1
                      },
                      {
                        "optionId": "B",
                        "description": "public int february() { return null;}",
                        "sequence": 2
                      }
                    ],
                    "answers": ["A",
                      "B"
                    ]
                  }
                                
                """;
        mockMvc.perform(
                        post("/v1/question")
                                .contentType(APPLICATION_JSON_VALUE)
                                .content(question))
                .andExpect(status().isBadRequest())
                .andReturn();
        verify(questionService, never()).saveQuestions(argumentCaptor.capture());
    }

    @Test
    void shouldReturn400WhenAnswerIsNull() throws Exception {
        String question = """
                                
                  {
                    "questionId": 1,
                    "question": "Which statements about the following class are true? (Choose all that apply.)",
                    "options": [
                      {
                        "optionId": "A",
                        "description": "public void january() { return; }",
                        "sequence": 1
                      },
                      {
                        "optionId": "B",
                        "description": "public int february() { return null;}",
                        "sequence": 2
                      }
                    ]
                  }
                                
                """;
        mockMvc.perform(
                        post("/v1/question")
                                .contentType(APPLICATION_JSON_VALUE)
                                .content(question))
                .andExpect(status().isBadRequest())
                .andReturn();
        verify(questionService, never()).saveQuestions(argumentCaptor.capture());
    }


    @Test
    void shouldPostAndReturn400WhenEmptyAnswer() throws Exception {
        String answer = """
                {
                  "type": "java 17",
                  "question": "Which of the following methods compile? (Choose all that apply.)",
                  "options": [
                    {
                      "optionId": "A",
                      "description": "public void january() { return; }",
                      "sequence": 1
                    },
                    {
                      "optionId": "B",
                      "description": "public int february() { return null;}",
                      "sequence": 2
                    },
                    {
                      "optionId": "C",
                      "description": "public void march() {}",
                      "sequence": 3
                    },
                    {
                      "optionId": "D",
                      "description": "public int april() { return 9;}",
                      "sequence": 4
                    },
                    {
                      "optionId": "E",
                      "description": "public int may() { return 9.0;}",
                      "sequence": 5
                    },
                    {
                      "optionId": "F",
                      "description": "public int june() { return;}",
                      "sequence": 6
                    }
                  ],
                  "answers" :[]
                                
                }
                """;
        mockMvc.perform(
                        post("/v1/question")
                                .contentType(APPLICATION_JSON_VALUE)
                                .content(answer))
                .andExpect(status().isBadRequest()).andReturn();

        verify(questionService, never()).saveQuestions(argumentCaptor.capture());
    }

    @Test
    public void shouldGetAndReturn200WhenControllerIsCalled() throws Exception {
        String examType = "Java 17";

        mockMvc.perform(get("/v1/questions").param("examType", examType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0))
                .andReturn();
    }

    @Test
    public void shouldGetAndReturn202WhenListOfQuestionsAreSaved() throws Exception {

        String questions = """
                [
                {
                "type": "java 17",
                  "question": "Which of the following methods compile? (Choose all that apply.)",
                  "options": [
                    {
                      "optionId": "A",
                      "description": "public void january() { return; }",
                      "sequence": 1
                    },
                    {
                      "optionId": "B",
                      "description": "public int february() { return null;}",
                      "sequence": 2
                    },
                    {
                      "optionId": "C",
                      "description": "public void march() {}",
                      "sequence": 3
                    },
                    {
                      "optionId": "D",
                      "description": "public int april() { return 9;}",
                      "sequence": 4
                    },
                    {
                      "optionId": "E",
                      "description": "public int may() { return 9.0;}",
                      "sequence": 5
                    },
                    {
                      "optionId": "F",
                      "description": "public int june() { return;}",
                      "sequence": 6
                    }
                  ],
                  "answers" :["A","B"]
                                
                }]
                """;

        mockMvc.perform(post("/v1/questions")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(questions))
                .andExpect(status()
                        .isAccepted())
                .andReturn();

        verify(questionService).saveQuestions(argumentCaptor.capture());

        assertEquals(1, argumentCaptor.getValue().size());
        assertEquals("Which of the following methods compile? (Choose all that apply.)", argumentCaptor.getValue().get(0).question());
        assertEquals("java 17", argumentCaptor.getValue().get(0).type());
        assertEquals(Arrays.asList("A", "B"), argumentCaptor.getValue().get(0).answers());

        assertEquals(6, argumentCaptor.getValue().get(0).options().size());

        assertEquals("A", argumentCaptor.getValue().get(0).options().get(0).optionId());
        assertEquals(1, argumentCaptor.getValue().get(0).options().get(0).sequence());
    }
    @Test
    public void shouldReturnBadRequestWhenQuestionIsNull() throws Exception {
        String questions = """
            [
                {
                "type": "java 17",
                
                  
                  "options": [
                    {
                      "optionId": "A",
                      "description": "public void january() { return; }",
                      "sequence": 1
                    },
                    {
                      "optionId": "B",
                      "description": "public int february() { return null;}",
                      "sequence": 2
                    },
                    {
                      "optionId": "C",
                      "description": "public void march() {}",
                      "sequence": 3
                    },
                    {
                      "optionId": "D",
                      "description": "public int april() { return 9;}",
                      "sequence": 4
                    },
                    {
                      "optionId": "E",
                      "description": "public int may() { return 9.0;}",
                      "sequence": 5
                    },
                    {
                      "optionId": "F",
                      "description": "public int june() { return;}",
                      "sequence": 6
                    }
                  ],
                  "answers" :["A","B"]
                                
                }]
            """;

        mockMvc.perform(post("/v1/questions")
                .contentType(APPLICATION_JSON_VALUE)
                .content(questions))
                .andExpect(status().isBadRequest())
                .andReturn();

        //you are telling to the mock that when above request has bad request
        // the mock should not call saveQuestions
        verify(questionService, never()).saveQuestions(anyList());

    }

    @Test
    void shouldReturnBadRequestWhenQuestionIsEmpty() throws Exception {
        String questions = """
            [
                {
                "type": "java 17",
                "question": "",
                  
                  "options": [
                    {
                      "optionId": "A",
                      "description": "public void january() { return; }",
                      "sequence": 1
                    },
                    {
                      "optionId": "B",
                      "description": "public int february() { return null;}",
                      "sequence": 2
                    },
                    {
                      "optionId": "C",
                      "description": "public void march() {}",
                      "sequence": 3
                    },
                    {
                      "optionId": "D",
                      "description": "public int april() { return 9;}",
                      "sequence": 4
                    },
                    {
                      "optionId": "E",
                      "description": "public int may() { return 9.0;}",
                      "sequence": 5
                    },
                    {
                      "optionId": "F",
                      "description": "public int june() { return;}",
                      "sequence": 6
                    }
                  ],
                  "answers" :["A","B"]
                                
                }]
            """;

        mockMvc.perform(post("/v1/questions")
                .contentType(APPLICATION_JSON_VALUE)
                .content(questions))
                .andExpect(status().isBadRequest())
                .andReturn();


    }


    }

