package com.project.estateapi.rest;

import com.project.estateapi.mapper.EstateMapper;
import com.project.estateapi.model.Estate;
import com.project.estateapi.rest.dto.CreateEstateRequest;
import com.project.estateapi.rest.dto.EstateDto;
import com.project.estateapi.service.EstateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.project.estateapi.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/estates")
@CrossOrigin(origins = "http://172.16.9.250:3000")
public class EstateController {

    private final EstateService estateService;
    private final EstateMapper estateMapper;

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping
    public List<EstateDto> getEstates(@RequestParam(value = "text", required = false) String text) {
        List<Estate> estates = (text == null) ? estateService.getEstates() : estateService.getEstatesContainingText(text);
        return estates.stream()
                .map(estateMapper::toEstateDto)
                .collect(Collectors.toList());
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public EstateDto createEstate(@Valid @RequestBody CreateEstateRequest createEstateRequest) {
        Estate estate = estateMapper.toEstate(createEstateRequest);
        estate.setId(UUID.randomUUID().toString());
        return estateMapper.toEstateDto(estateService.saveEstate(estate));
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @DeleteMapping("/{id}")
    public EstateDto deleteEstate(@PathVariable String id) {
        Estate estate = estateService.validateAndGetEstate(id);
        estateService.deleteEstate(estate);
        return estateMapper.toEstateDto(estate);
    }
}
