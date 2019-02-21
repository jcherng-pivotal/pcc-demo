package io.pivotal.demo.pcc.loader.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.gemfire.function.execution.GemfireOnRegionFunctionTemplate;
import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

public interface PagingLoadService<S, T> {
    Boolean DEFAULT_CLEAR_BY_FUNCTION = true;

    JpaRepository<S, ?> getSourceRepository();

    GemfireRepository<T, ?> getTargetRepository();

    Sort getDefaultSort();

    T map(S source);

    GemfireOnRegionFunctionTemplate getRegionFunctionTemplate();

    default Boolean filter(S source) {
        return true;
    }

    default Boolean clear() {
        if (DEFAULT_CLEAR_BY_FUNCTION) {
            getRegionFunctionTemplate().execute("ClearRegionFunction");
        } else {
            getTargetRepository().deleteAll(getTargetRepository().findAll());
        }
        return getTargetRepository().count() == 0;
    }

    default void load() {
        load(getSourceRepository(), getTargetRepository(), getDefaultSort());
    }

    default void load(JpaRepository<S, ?> sourceRepository,
                      GemfireRepository<T, ?> targetRepository,
                      Sort sort) {
        long recordCount = sourceRepository.count();
        if (recordCount > 0 && clear()) {
            int recordCountPerPage = 5000;
            long pageCount = (recordCount + recordCountPerPage - 1) / recordCountPerPage;
            for (int i = 0; i < pageCount; i++) {
                List<T> targetList = sourceRepository
                        .findAll(PageRequest.of(i,
                                recordCountPerPage,
                                sort))
                        .getContent()
                        .stream()
                        .filter(s -> filter(s))
                        .map(s -> map(s))
                        .collect(Collectors.toList());
                targetRepository.saveAll(targetList);
            }
        }
    }
}
