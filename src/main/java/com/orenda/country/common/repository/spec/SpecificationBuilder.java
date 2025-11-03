package com.orenda.country.common.repository.spec;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SpecificationBuilder<T> {
    private final List<Specification<T>> specifications = new ArrayList<>();
    public static <T> SpecificationBuilder<T> builder(){return new SpecificationBuilder<>();}


    public SpecificationBuilder<T> searchAll(String field, Object value){
        String pattern = "%"+value+"%";
        if(value !=null){
            specifications.add((root, query, cb) ->
                    cb.like(cb.lower(root.get(field)),  pattern));
        }
        return this;
    }


    // ======= BUILD ========
    public Specification<T> buildAnd() {
        if (specifications.isEmpty()) {
            return all();
        }
        return specifications.stream()
                .reduce(Specification::and)
                .orElse(all());

    }
    public Specification<T> buildOr(){
        if(specifications.isEmpty()){
            return all();
        }
        return specifications.stream()
                .reduce(Specification::or)
                .orElse(all());
    }


    // =========== UTILITY ==========
    public Specification<T> all(){
        return (root, query, cb)->cb.conjunction();
    }

}
