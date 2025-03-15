package org.example.ui;

import org.example.service.StudentService;

import org.example.model.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class StudentManagementUI {
    private StudentService studentService;
    private DefaultListModel<String> listModel;
    private JList<String> studentList;
    private JTextField nameField, ageField, courseField, gradeField;

    public StudentManagementUI() {
        studentService = new StudentService();

        // Main Frame
        JFrame frame = new JFrame("Student Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        // **Panel 1: Student List Display**
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());
        listModel = new DefaultListModel<>();
        studentList = new JList<>(listModel);
        loadStudents();
        displayPanel.add(new JScrollPane(studentList), BorderLayout.CENTER);

        // **Panel 2: Add Student Form**
        JPanel addPanel = new JPanel();
        addPanel.setLayout(new GridLayout(5, 2, 5, 5));
        nameField = new JTextField();
        ageField = new JTextField();
        courseField = new JTextField();
        gradeField = new JTextField();
        JButton addButton = new JButton("Add");

        addPanel.add(new JLabel("Name:"));
        addPanel.add(nameField);
        addPanel.add(new JLabel("Age:"));
        addPanel.add(ageField);
        addPanel.add(new JLabel("Course:"));
        addPanel.add(courseField);
        addPanel.add(new JLabel("Grade:"));
        addPanel.add(gradeField);
        addPanel.add(new JLabel());  // Empty space
        addPanel.add(addButton);

        // **Panel 3: Update & Delete Buttons**
        JPanel actionPanel = new JPanel();
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        actionPanel.add(updateButton);
        actionPanel.add(deleteButton);

        // Add panels to frame
        frame.add(displayPanel, BorderLayout.CENTER);
        frame.add(addPanel, BorderLayout.SOUTH);
        frame.add(actionPanel, BorderLayout.NORTH);

        // **Event Listeners**
        addButton.addActionListener(e -> addStudent());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());

        frame.setVisible(true);
    }

    // Load Students into JList
    private void loadStudents() {
        listModel.clear();
        List<Student> students = studentService.getAllStudents();
        for (Student student : students) {
            listModel.addElement(student.getId() + ": " + student.getName() + " - " + student.getCourse());
        }
    }

    // Add Student
    private void addStudent() {
        String name = nameField.getText();
        int age = Integer.parseInt(ageField.getText());
        String course = courseField.getText();
        String grade = gradeField.getText();

        Student student = new Student(0, name, age, course, grade);
        studentService.addStudent(student);
        loadStudents();
        clearFields();
    }

    // Update Student
    private void updateStudent() {
        int selectedIndex = studentList.getSelectedIndex();
        if (selectedIndex != -1) {
            String selectedValue = studentList.getSelectedValue();
            int id = Integer.parseInt(selectedValue.split(":")[0]);

            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String course = courseField.getText();
            String grade = gradeField.getText();

            Student student = new Student(id, name, age, course, grade);
            studentService.updateStudent(student);
            loadStudents();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(null, "Select a student to update.");
        }
    }

    // Delete Student
    private void deleteStudent() {
        int selectedIndex = studentList.getSelectedIndex();
        if (selectedIndex != -1) {
            String selectedValue = studentList.getSelectedValue();
            int id = Integer.parseInt(selectedValue.split(":")[0]);

            studentService.deleteStudent(id);
            loadStudents();
        } else {
            JOptionPane.showMessageDialog(null, "Select a student to delete.");
        }
    }

    // Clear Input Fields
    private void clearFields() {
        nameField.setText("");
        ageField.setText("");
        courseField.setText("");
        gradeField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentManagementUI::new);
    }
}



