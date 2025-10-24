package com.orenda.country.specification;

import com.orenda.country.entity.Wards;
import org.springframework.data.jpa.domain.Specification;

public class WardSpecification {
    public static Specification<Wards> searchAll(String search){
        return (root, query, cb) ->{
            String pattern = "%"+search+"%";
            if(search!=null && !search.trim().isEmpty()){
                return cb.or(cb.like(cb.lower(root.get("name")), pattern),
                        cb.like(cb.lower(root.get("code")), pattern),
                        cb.like(cb.lower(cb.concat(root.get("id").as(String.class),"")), pattern));
            }
            return cb.conjunction();
        };
    }
    public static Specification<Wards> filterProvinceCode(String provinceCode){
        return (root, query, cb)->{
            String pattern = "%"+provinceCode+"%";
          if(provinceCode!=null && !provinceCode.trim().isEmpty()){
              return cb.like(cb.lower(root.get("provinceCode")), pattern);
          }
          return cb.conjunction();
        };
    }
}
