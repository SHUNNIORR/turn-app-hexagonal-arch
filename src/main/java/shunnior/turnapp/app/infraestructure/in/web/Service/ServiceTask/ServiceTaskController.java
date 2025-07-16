package shunnior.turnapp.app.infraestructure.in.web.Service.ServiceTask;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shunnior.turnapp.app.domain.services.ServiceTask;
import shunnior.turnapp.app.domain.services.dto.AssignEmployeeResponse;
import shunnior.turnapp.app.domain.services.dto.CreateServiceRequest;
import shunnior.turnapp.app.domain.services.dto.ServiceResponse;
import shunnior.turnapp.app.domain.services.in.ServiceTaskUseCase;
import shunnior.turnapp.app.infraestructure.out.persistance.user.UserEntity;

@RestController
@RequestMapping("/api/admin/service")
@RequiredArgsConstructor
public class ServiceTaskController {
    private final ServiceTaskUseCase serviceTaskUseCase;

    @PostMapping
    public ResponseEntity<ServiceResponse> createService(
            @RequestBody CreateServiceRequest request,
            @AuthenticationPrincipal UserEntity userEntity
    ) {

        ServiceTask created = serviceTaskUseCase.createService(request, userEntity.getId());

        return ResponseEntity.ok(new ServiceResponse(
                created.getId(),
                created.getDescription(),
                userEntity.getEmail(),
                created.getStatus()
        ));
    }

    @PostMapping("/assign/{serviceId}")
    public ResponseEntity<AssignEmployeeResponse> assignRandomEmployee(@PathVariable Integer serviceId) {
        AssignEmployeeResponse response = serviceTaskUseCase.assignRandomEmployee(serviceId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/close/{serviceId}")
    public ResponseEntity<String> closeService(
            @PathVariable Integer serviceId,
            @AuthenticationPrincipal UserEntity userEntity
    ) {
        return ResponseEntity.ok(
                serviceTaskUseCase.closeService(
                        serviceId,
                        userEntity.getId(),
                        userEntity.getEmail()
                )
        );
    }
}
