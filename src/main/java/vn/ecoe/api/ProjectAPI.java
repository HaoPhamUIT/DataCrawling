package vn.ecoe.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import vn.ecoe.response.ResponseModel;
import vn.ecoe.utils.ApiConstants;


@Api(value = "Project", description = "Project APIs")
public interface ProjectAPI {

    @ApiOperation(value = "Get an All Project", response = ResponseModel.class)
    @GetMapping(path = ApiConstants.PROJECT_END_POINT)
    ResponseModel findAllProject();

}
