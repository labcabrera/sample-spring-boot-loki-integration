package org.labcabrera.sample.archetype.casestep.interfaces.http;

import org.labcabrera.sample.archetype.casestep.interfaces.http.dto.CaseStepDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CaseStepController implements CaseStepControllerDefinition {

    @Override
    public ResponseEntity<CaseStepDto> getCaseStepById(String caseStepId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCaseStepById'");
    }

    @Override
    public ResponseEntity<Page<CaseStepDto>> getCaseStepsByCaseFolderId(String caseFolderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCaseStepsByCaseFolderId'");
    }

}
