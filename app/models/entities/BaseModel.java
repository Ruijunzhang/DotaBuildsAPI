package models.entities;

import io.ebean.Model;

import javax.persistence.*;

@MappedSuperclass
public class BaseModel extends Model {

    @Id
    @Column(name = "match_id")
    public Long id;
}
