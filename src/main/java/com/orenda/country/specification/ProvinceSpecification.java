package com.orenda.country.specification;


import com.orenda.country.entity.Provinces;
import org.springframework.data.jpa.domain.Specification;

public class ProvinceSpecification {
    public static Specification<Provinces> searchAllFiels(String search){
        return (root, query, cb)->{
            String likePattern = "%"+search+"%";
            if(search !=null && !search.trim().isEmpty()){
                return cb.or(cb.like(cb.lower(root.get("name")),likePattern ),
                        cb.like(cb.lower(root.get("code")),likePattern),
                        cb.like(cb.lower(cb.concat(root.get("id").as(String.class),"")), likePattern));
            }
            return cb.conjunction();
        };
    }
}
