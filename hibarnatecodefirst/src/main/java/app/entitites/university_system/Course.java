package app.entitites.university_system;

import javax.persistence.*;

@Entity
@Table(name = "courses")
public class Course {
    
    private Integer id;
    private Teacher teacher;

    public Course() {
    }

    @ManyToOne()
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
