package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Course;
import model.RunPlan;
import model.Module;
import model.StudentProfile;
import view.FinalYearOptionsRootPane;
import view.OverviewSelectionPane;
import view.ReserveModulesPane;
import view.SelectModulesPane;
import view.CreateStudentProfilePane;
import view.FinalYearOptionsMenuBar;

public class FinalYearOptionsController {

	// fields to be used throughout class
	private FinalYearOptionsRootPane view;
	private StudentProfile model;
	private CreateStudentProfilePane cspp;
	private SelectModulesPane smp;
	private ReserveModulesPane rmp;
	private OverviewSelectionPane osp;
	private FinalYearOptionsMenuBar mstmb;
	private Boolean modelCreated = false;

	public FinalYearOptionsController(StudentProfile model, FinalYearOptionsRootPane view) {

		// initialise view and model fields
		this.view = view;
		this.model = model;

		// initialise view subcontainer fields
		cspp = view.getCreateStudentProfilePane();
		smp = view.getSelectModulesPane();
		rmp = view.getReserveModulesPane();
		osp = view.getOverviewSelectionPane();
		mstmb = view.getModuleSelectionToolMenuBar();

		// add Courses to combobox in create student profile pane using the
		// buildModulesAndCourses helper method below
		cspp.addCourseDataToComboBox(buildModulesAndCourses());

		// attach event handlers to view using private helper method
		this.attachEventHandlers();
	}

	// helper method - used to attach event handlers
	private void attachEventHandlers() {

		// attach an event handler to the create student profile pane
		cspp.addCreateStudentProfileHandler(new CreateStudentProfileHandler());

		// attach event handlers to the pane
		smp.addResetButtonHandler(new ResetButtonHandler());
		smp.addAddT1ButtonHandler(new AddT1ButtonHandler());
		smp.addAddT2ButtonHandler(new AddT2ButtonHandler());
		smp.addRemoveT1ButtonHandler(new RemoveT1ButtonHandler());
		smp.addRemoveT2ButtonHandler(new RemoveT2ButtonHandler());
		smp.addSubmitButtonHandler(new SubmitButtonHandler());

		// attach event handlers to the pane
		rmp.addAddT1ButtonHandler(new AddT1ButtonHandlerReserve());
		rmp.addAddT2ButtonHandler(new AddT2ButtonHandlerReserve());
		rmp.addRemoveT1ButtonHandler(new RemoveT1ButtonHandlerReserve());
		rmp.addRemoveT2ButtonHandler(new RemoveT2ButtonHandlerReserve());
		rmp.addConfirmT1ButtonHandler(new ConfirmT1ButtonHandler());
		rmp.addConfirmT2ButtonHandler(new ConfirmT2ButtonHandler());

		// attach event handler to the pane
		osp.addSaveOverviewButtonHandler(new SaveOverviewButtonHandler());

		// attach event handlers to the menu bar
		mstmb.addExitHandler(e -> System.exit(0));
		mstmb.addAboutHandler(new aboutHandler());
		mstmb.addSaveHandler(new saveHandler());
		mstmb.addLoadHandler(new loadHandler());
	}

	private class CreateStudentProfileHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {

			// Create a new student profile with the retrieved values
			if ((cspp.getStudentName().getFirstName().isBlank() == false)
					&& (cspp.getStudentName().getFamilyName().isBlank() == false)
					&& (cspp.getStudentPnumber().isBlank() == false) && (cspp.getStudentEmail().isBlank() == false)
					&& (cspp.getStudentDate() != null)) {
				model = new StudentProfile();
				model.setStudentCourse(cspp.getSelectedCourse());
				model.setStudentPnumber(cspp.getStudentPnumber());
				model.setStudentName(cspp.getStudentName());
				model.setStudentEmail(cspp.getStudentEmail());
				model.setSubmissionDate(cspp.getStudentDate());
				modelCreated = true;
				smp.setMessage("");
				rmp.clearAllData();
				rmp.setMessage1("");
				rmp.setMessage2("");
				osp.clearAllData();
				osp.setMessage("");
				osp.updateProfileOverview(model.getStudentName(), model.getStudentPnumber(), model.getStudentEmail(),
						model.getSubmissionDate().toString(), model.getStudentCourse().toString());

				// Iterate through AllModules and get all course types and populate smp
				Collection<Module> AllModules = model.getStudentCourse().getAllModulesOnCourse();
				Collection<Module> T1Modules = new ArrayList<>();
				Collection<Module> T2Modules = new ArrayList<>();
				Collection<Module> SelectedT1Modules = new ArrayList<>();
				Collection<Module> SelectedT2Modules = new ArrayList<>();
				Collection<Module> YearModule = new ArrayList<>();
				int T1Credits = 0;
				int T2Credits = 0;
				for (Module module : AllModules) {
					if (module.getDelivery() == RunPlan.TERM_1) {
						if (module.isMandatory() == false) {
							T1Modules.add(module);
						} else if (module.isMandatory() == true) {
							// model.addSelectedModule(module);
							SelectedT1Modules.add(module);
							model.addSelectedModule(module);
							T1Credits += module.getModuleCredits();
						}
					} else if (module.getDelivery() == RunPlan.TERM_2) {
						if (module.isMandatory() == false) {
							T2Modules.add(module);
						} else if (module.isMandatory() == true) {
							// model.addSelectedModule(module);
							SelectedT2Modules.add(module);
							model.addSelectedModule(module);
							T2Credits += module.getModuleCredits();
						}
					} else if (module.getDelivery() == RunPlan.YEAR_LONG) {
						// model.addSelectedModule(module);
						YearModule.add(module);
						model.addSelectedModule(module);
						T1Credits += 15;
						T2Credits += 15;
					}
				}

				smp.addModuleDataTolstUnselectedT1(T1Modules);
				smp.addModuleDataTolstSelectedT1(SelectedT1Modules);
				smp.addModuleDataTolstUnselectedT2(T2Modules);
				smp.addModuleDataTolstSelectedT2(SelectedT2Modules);
				smp.addModuleDataTolstYearModule(YearModule);
				smp.addT1ModuleCredits(T1Credits);
				smp.addT2ModuleCredits(T2Credits);
				cspp.setMessage("Student Profile Created!");
				view.changeTab(1);
			} else {
				cspp.setMessage("Please Fill In All Fields!");
			}
		}
	}

	private class ResetButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (modelCreated == true) {
				// Iterate through AllModules and get all Modules
				Collection<Module> AllModules = model.getStudentCourse().getAllModulesOnCourse();
				Collection<Module> T1Modules = new ArrayList<>();
				Collection<Module> T2Modules = new ArrayList<>();
				Collection<Module> SelectedT1Modules = new ArrayList<>();
				Collection<Module> SelectedT2Modules = new ArrayList<>();
				Collection<Module> YearModule = new ArrayList<>();
				int T1Credits = 0;
				int T2Credits = 0;
				for (Module module : AllModules) {
					if (module.getDelivery() == RunPlan.TERM_1) {
						if (module.isMandatory() == false) {
							T1Modules.add(module);
						} else if (module.isMandatory() == true) {
							SelectedT1Modules.add(module);
							T1Credits += module.getModuleCredits();
						}
					} else if (module.getDelivery() == RunPlan.TERM_2) {
						if (module.isMandatory() == false) {
							T2Modules.add(module);
						} else if (module.isMandatory() == true) {
							SelectedT2Modules.add(module);
							T2Credits += module.getModuleCredits();
						}
					} else if (module.getDelivery() == RunPlan.YEAR_LONG) {
						YearModule.add(module);
						T1Credits += 15;
						T2Credits += 15;
					}
				}
				smp.addModuleDataTolstUnselectedT1(T1Modules);
				smp.addModuleDataTolstSelectedT1(SelectedT1Modules);
				smp.addModuleDataTolstUnselectedT2(T2Modules);
				smp.addModuleDataTolstSelectedT2(SelectedT2Modules);
				smp.addModuleDataTolstYearModule(YearModule);
				smp.addT1ModuleCredits(T1Credits);
				smp.addT2ModuleCredits(T2Credits);
				smp.setMessage("Module Selection Reset!");
			}
		}

	}

	private class AddT1ButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Module selectedModule = smp.getTheUnselectedT1Module();
			if (selectedModule != null) {
				int T1Credits = smp.getTxtT1Credits();
				if (T1Credits >= 60) {
					smp.setMessage("Maximum Term Credits Reached!");
				} else {
					smp.addT1ModuleToSelected(selectedModule);
					smp.addT1ModuleCredits(T1Credits += selectedModule.getModuleCredits());
					smp.setMessage("");
				}
			} else {
				smp.setMessage("Please select a Module!");
			}
		}
	}

	private class AddT2ButtonHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {
			Module selectedModule = smp.getTheUnselectedT2Module();
			if (selectedModule != null) {
				int T2Credits = smp.getTxtT2Credits();
				if (T2Credits >= 60) {
					smp.setMessage("Maximum Term Credits Reached!");
				} else {
					smp.addT2ModuleToSelected(selectedModule);
					smp.addT2ModuleCredits(T2Credits += selectedModule.getModuleCredits());
					smp.setMessage("");
				}
			} else {
				smp.setMessage("Please Select A Module!");
			}

		}
	}

	private class RemoveT1ButtonHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {
			Module selectedModule = smp.getTheSelectedT1Module();
			if (selectedModule != null) {
				int T1Credits = smp.getTxtT1Credits();
				if (selectedModule.isMandatory() == true) {
					smp.setMessage("Selected Module Is Mandatory!");
				} else {
					smp.removeT1ModuleFromSelected(selectedModule);
					smp.addT1ModuleCredits(T1Credits -= selectedModule.getModuleCredits());
					smp.setMessage("");
				}
			} else {
				smp.setMessage("Please Select A Module!");
			}
		}
	}

	private class RemoveT2ButtonHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {
			Module selectedModule = smp.getTheSelectedT2Module();
			if (selectedModule != null) {
				int T2Credits = smp.getTxtT2Credits();
				if (selectedModule.isMandatory() == true) {
					smp.setMessage("Module Is Mandatory!");
				} else {
					smp.removeT2ModuleFromSelected(selectedModule);
					smp.addT2ModuleCredits(T2Credits -= selectedModule.getModuleCredits());
					smp.setMessage("");
				}
			} else {
				smp.setMessage("Please Select A Module!");
			}
		}
	}

	private class SubmitButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (modelCreated == true) {
				if ((smp.getTxtT1Credits() == 60) && (smp.getTxtT2Credits() == 60)) {
					model.clearSelectedModules();
					Collection<Module> AllModules = model.getStudentCourse().getAllModulesOnCourse();
					Collection<Module> UnselectedT1 = new ArrayList<>();
					Collection<Module> UnselectedT2 = new ArrayList<>();
					List<Module> SelectedT1 = smp.getSelectedT1Modules();
					List<Module> SelectedT2 = smp.getSelectedT2Modules();
					List<Module> SelectedYear = smp.getSelectedYearModules();
					for (Module module : AllModules) {
						if (module.getDelivery() == RunPlan.TERM_1) {
							UnselectedT1.add(module);
						} else if (module.getDelivery() == RunPlan.TERM_2) {
							UnselectedT2.add(module);
						}
					}
					for (Module module : SelectedT1) {
						model.addSelectedModule(module);
						UnselectedT1.remove(module);
					}
					for (Module module : SelectedT2) {
						model.addSelectedModule(module);
						UnselectedT2.remove(module);
					}
					for (Module module : SelectedYear) {
						model.addSelectedModule(module);
					}
					rmp.clearAll();
					rmp.addModuleDataTolstUnselectedT1(UnselectedT1);
					rmp.addModuleDataTolstUnselectedT2(UnselectedT2);
					osp.updateSelectedOverview(model.getAllSelectedModules());
					osp.clearReservedOverview();
					smp.setMessage("Selected Modules Submitted!");
					view.changeTab(2);
					rmp.changePane(0);
				} else {
					smp.setMessage("Select 60 Credits For Each Term!");
				}
			}

		}
	}

	private class AddT1ButtonHandlerReserve implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {
			Module selectedModule = rmp.getSelectedT1Module();
			if (selectedModule != null) {
				if (rmp.getReservedT1Credits() < 30) {
					rmp.addT1ModuleToReserved(selectedModule);
					rmp.setMessage1("");
				} else {
					rmp.setMessage1("Maximum 30 credits of Reserved Modules!");
				}
			} else {
				rmp.setMessage1("Please Select A Module!");
			}
		}
	}

	private class AddT2ButtonHandlerReserve implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {
			Module selectedModule = rmp.getSelectedT2Module();
			if (selectedModule != null) {
				if (rmp.getReservedT2Credits() < 30) {
					rmp.addT2ModuleToReserved(selectedModule);
					rmp.setMessage2("");
				} else {
					rmp.setMessage2("Maximum 30 credits of Reserved Modules!");
				}
			} else {
				rmp.setMessage2("Please Select A Module!");
			}
		}
	}

	private class RemoveT1ButtonHandlerReserve implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {
			Module selectedModule = rmp.getTheReservedT1Module();
			if (selectedModule != null) {
				rmp.removeT1ModuleFromReserved(selectedModule);
				rmp.setMessage1("");
			} else {
				rmp.setMessage1("Please Select A Module!");
			}
		}
	}

	private class RemoveT2ButtonHandlerReserve implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {
			Module selectedModule = rmp.getTheReservedT2Module();
			if (selectedModule != null) {
				rmp.removeT2ModuleFromReserved(selectedModule);
				rmp.setMessage2("");
			} else {
				rmp.setMessage2("Please Select A Module!");
			}
		}
	}

	private class ConfirmT1ButtonHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {
			if (rmp.getReservedT1Credits() >= 30) {
				List<Module> ReservedT1 = rmp.getReservedT1Modules();
				Collection<Module> AllReserved = new ArrayList<>();
				for (Module module : model.getAllReservedModules()) {
					if (module.getDelivery() != RunPlan.TERM_1) {
						AllReserved.add(module);
					}
				}
				model.clearReservedModules();
				for (Module module : AllReserved) {
					model.addReservedModule(module);
				}
				for (Module module : ReservedT1) {
					model.addReservedModule(module);
				}
				osp.updateReservedOverview(model.getAllReservedModules());
				rmp.setMessage1("Term 1 Reserved Modules Confirmed!");
				rmp.changePane(1);
			} else {
				rmp.setMessage1("Please Select 30 Credits Of Reserved Modules!");
			}
		}
	}

	private class ConfirmT2ButtonHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {
			if (rmp.getReservedT2Credits() >= 30) {
				List<Module> ReservedT2 = rmp.getReservedT2Modules();
				Collection<Module> AllReserved = new ArrayList<>();
				for (Module module : model.getAllReservedModules()) {
					if (module.getDelivery() != RunPlan.TERM_2) {
						AllReserved.add(module);
					}
				}
				model.clearReservedModules();
				for (Module module : AllReserved) {
					model.addReservedModule(module);
				}
				for (Module module : ReservedT2) {
					model.addReservedModule(module);
				}
				osp.updateReservedOverview(model.getAllReservedModules());
				rmp.setMessage2("Term 2 Reserved Modules Confirmed!");
				view.changeTab(3);
				rmp.changePane(2);
			} else {
				rmp.setMessage2("Please Select 30 Credits Of Reserved Modules!");
			}
		}
	}

	private class SaveOverviewButtonHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {
			// https://stackoverflow.com/questions/11496700/how-to-use-printwriter-and-file-classes-in-java
			try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(
					model.getStudentName() + " " + model.getStudentPnumber() + " Module Selection.txt")))) {
				writer.println(osp.getProfileOverview() + "\n\n" + osp.getSelectedOverview() + "\n\n"
						+ osp.getReservedOverview());
				osp.setMessage("Successfully Saved Overview To Text File!");
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	private class aboutHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {
			String aboutApp = ("This application is designed for Computer science and Software Engineering students to select their final year modules:\n\n"
					+ "-First enter your details in the create student profile tab\n"
					+ "-Then eelect your preferred modules from the select modules tab\n"
					+ "-Then select your reserve modules from the reserve modules tab\n"
					+ "-The final tab, overview selection, will then display all your selected details\n"
					+ "-You can also save and load your progress from the file menu");
			String helpTitle = ("About Final Year Module Selection Tool");
			mstmb.aboutPopup(helpTitle, aboutApp);
		}
	}

	private class saveHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {
			// https://www.digitalocean.com/community/tutorials/objectoutputstream-java-write-object-file
			try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("model.bin"))) {
				outputStream.writeObject(model);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private class loadHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {
			// https://www.codejava.net/java-se/file-io/how-to-read-and-write-binary-files-in-java
			try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("model.bin"))) {
				model = (StudentProfile) inputStream.readObject();
				modelCreated = false;
				int index = 3;
				if (model.getStudentCourse().getCourseName().equals("Computer Science")) {
					index = 0;
					modelCreated = true;
				}
				if (model.getStudentCourse().getCourseName().equals("Software Engineering")) {
					index = 1;
					modelCreated = true;
				}
				cspp.setFields(model.getStudentFirstName(), model.getStudentSurname(), model.getStudentEmail(),
						model.getStudentPnumber(), model.getSubmissionDate(), index);
				Collection<Module> AllModules = model.getStudentCourse().getAllModulesOnCourse();
				Collection<Module> T1Modules = new ArrayList<>();
				Collection<Module> T2Modules = new ArrayList<>();
				Collection<Module> SelectedT1Modules = new ArrayList<>();
				Collection<Module> SelectedT2Modules = new ArrayList<>();
				Collection<Module> YearModule = new ArrayList<>();
				int T1Credits = 0;
				int T2Credits = 0;
				for (Module module : model.getAllSelectedModules()) {
					if (module.getDelivery() == RunPlan.TERM_1) {
						SelectedT1Modules.add(module);
						T1Credits += module.getModuleCredits();
					} else if (module.getDelivery() == RunPlan.TERM_2) {
						SelectedT2Modules.add(module);
						T2Credits += module.getModuleCredits();
					} else if (module.getDelivery() == RunPlan.YEAR_LONG) {
						YearModule.add(module);
						T1Credits += 15;
						T2Credits += 15;
					}
				}
				for (Module module : AllModules) {
					int i = 1;
					for (Module module1 : model.getAllSelectedModules()) {
						i = i * (module1.compareTo(module));
					}
					if (i != 0) {
						if (module.getDelivery() == RunPlan.TERM_1) {
							T1Modules.add(module);
						} else if (module.getDelivery() == RunPlan.TERM_2) {
							T2Modules.add(module);
						}
					}

				}

				smp.addModuleDataTolstUnselectedT1(T1Modules);
				smp.addModuleDataTolstSelectedT1(SelectedT1Modules);
				smp.addModuleDataTolstUnselectedT2(T2Modules);
				smp.addModuleDataTolstSelectedT2(SelectedT2Modules);
				smp.addModuleDataTolstYearModule(YearModule);
				smp.addT1ModuleCredits(T1Credits);
				smp.addT2ModuleCredits(T2Credits);
				smp.setMessage("");

				Collection<Module> AllUnselectedModules = T1Modules;
				AllUnselectedModules.addAll(T2Modules);
				Collection<Module> UnreservedT1 = new ArrayList<>();
				Collection<Module> UnreservedT2 = new ArrayList<>();
				List<Module> ReservedT1 = new ArrayList<>();
				List<Module> ReservedT2 = new ArrayList<>();

				for (Module module : model.getAllReservedModules()) {
					if (module.getDelivery() == RunPlan.TERM_1) {
						ReservedT1.add(module);
					} else if (module.getDelivery() == RunPlan.TERM_2) {
						ReservedT2.add(module);
					}
				}
				for (Module module : AllUnselectedModules) {
					int i = 1;
					for (Module module1 : model.getAllReservedModules()) {
						i = i * (module1.compareTo(module));
					}
					if (i != 0) {
						if (module.getDelivery() == RunPlan.TERM_1) {
							UnreservedT1.add(module);
						} else if (module.getDelivery() == RunPlan.TERM_2) {
							UnreservedT2.add(module);
						}
					}

				}

				rmp.clearAll();
				rmp.addModuleDataTolstUnselectedT1(UnreservedT1);
				rmp.addModuleDataTolstUnselectedT2(UnreservedT2);
				rmp.addModuleDataTolstReservedT1(ReservedT1);
				rmp.addModuleDataTolstReservedT2(ReservedT2);
				rmp.setMessage1("");
				rmp.setMessage2("");

				osp.updateReservedOverview(model.getAllReservedModules());
				osp.updateSelectedOverview(model.getAllSelectedModules());
				osp.updateProfileOverview(model.getStudentName(), model.getStudentPnumber(), model.getStudentEmail(),
						model.getSubmissionDate().toString(), model.getStudentCourse().toString());

			} catch (IOException | ClassNotFoundException ex) {
				ex.printStackTrace();
			}
		}
	}

	private Course[] buildModulesAndCourses() {
		// https://stackoverflow.com/questions/16104616/using-bufferedreader-to-read-text-file
		List<Course> courses = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader("module_data.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] moduleData = line.split(", ");

				String courseName = moduleData[0];
				String moduleCode = moduleData[1];
				String moduleName = moduleData[2];
				int moduleCredits = Integer.parseInt(moduleData[3]);
				boolean isMandatory = Boolean.parseBoolean(moduleData[4]);
				RunPlan runPlan = RunPlan.valueOf(moduleData[5]);

				Course course = findOrCreateCourse(courses, courseName);

				Module module = new Module(moduleCode, moduleName, moduleCredits, isMandatory, runPlan);
				course.addModuleToCourse(module);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return courses.toArray(new Course[0]);
	}

	private Course findOrCreateCourse(List<Course> courses, String courseName) {
		for (Course course : courses) {
			if (course.getCourseName().equals(courseName)) {
				return course;
			}
		}

		Course newCourse = new Course(courseName);
		courses.add(newCourse);
		return newCourse;
	}

}
