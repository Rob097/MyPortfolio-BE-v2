package com.myprojects.myportfolio.core.education;

import com.myprojects.myportfolio.clients.education.EducationQ;
import com.myprojects.myportfolio.clients.education.EducationR;
import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Synthetic;
import com.myprojects.myportfolio.core.education.mappers.EducationRMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.myprojects.myportfolio.clients.general.IController.VIEW;

@RestController
@RequestMapping("api/core/educations")
@Slf4j
public class EducationController {

    @Autowired
    private EducationService educationService;

    @Autowired
    private EducationRMapper educationRMapper;


    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<EducationR>> get(
            @PathVariable("id") Integer id,
            @RequestParam(name = VIEW, required = false, defaultValue = Synthetic.name) IView view,
            EducationQ parameters) throws Exception {
        Validate.notNull(id, "Mandatory parameter is missing: id.");
        Validate.notNull(parameters, "Mandatory parameter is missing: parameters.");

        Education education = this.educationService.findById(id);
        MessageResource<EducationR> result = new MessageResource<>(educationRMapper.map(education));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
