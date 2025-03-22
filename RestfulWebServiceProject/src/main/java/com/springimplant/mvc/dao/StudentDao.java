package com.springimplant.mvc.dao;

import com.springimplant.mvc.model.Student;
import lombok.Data;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Data
@EnableTransactionManagement
public class StudentDao {

    private HibernateTemplate hibernateTemplate;

    @Transactional
    public Integer insert(Student student) {
        return (Integer) this.hibernateTemplate.save(student);
    }

    public Student getStudent(int studentId) {
        return this.hibernateTemplate.get(Student.class, studentId);
    }

    public List<Student> getAllStudents() {
        return this.hibernateTemplate.loadAll(Student.class);
    }

    @Transactional
    public void deleteStudent(int studentId) {
        Student stu = this.hibernateTemplate.get(Student.class, studentId);
        this.hibernateTemplate.delete(stu);
    }

    @Transactional
    public void updateStudent(Student student) {
        this.hibernateTemplate.update(student);
    }

}
