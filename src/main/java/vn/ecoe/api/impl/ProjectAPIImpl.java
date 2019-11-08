package vn.ecoe.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ecoe.api.ProjectAPI;
import vn.ecoe.response.ResponseModel;
import vn.ecoe.service.ProjectService;
import vn.ecoe.utils.ApiConstants;

/**
 * @author locld
 */
@RestController
@RequestMapping(value = ApiConstants.API_VERSION_1)
public class ProjectAPIImpl implements ProjectAPI {
    @Autowired
    ProjectService projectService;

    @Override
    public ResponseModel findAllProject() {
        return new ResponseModel(projectService.getAll());
    }


}
