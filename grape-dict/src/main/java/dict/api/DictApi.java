package dict.api;

import dict.domain.Dict;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dict")
public final class DictApi {
    @RequestMapping(value = "", method = RequestMethod.GET)
    public HttpStatus list() {
        return HttpStatus.CREATED;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public HttpStatus add(Dict dict) {
        dict.save();
        return HttpStatus.CREATED;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void update(Dict dict) {
        dict.save();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(Long id) {
        Dict.db().delete(Dict.class, id);
    }
}
