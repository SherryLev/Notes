//A1

import javafx.application.Application
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.Group
import javafx.scene.text.Font
import javafx.scene.control.Separator;
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.CheckBox
import javafx.scene.control.ToolBar
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.scene.Scene
import javafx.geometry.Orientation
import javafx.scene.control.ListView
import javafx.geometry.Pos
import javafx.scene.control.TextArea
import javafx.scene.control.ScrollPane
import javafx.scene.effect.ColorInput
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment
import javafx.stage.Stage


class Main : Application() {
    // main entry point for an JavaFX application
    // this is the only base method that needs to be implemented
    override fun start(stage: Stage) {
        var isList : Boolean = true
        var totalNotes: Int = 4
        var nonArcNotes : Int  = 2

        // Setting up toolbar - using CS349 Fall 22 public repository
        val toolbar = ToolBar()
        // group 1 (list and grid)
        val viewLabel = Label("View:")
        val listButton = Button("List")
        listButton.prefWidth = 50.0
        val gridButton = Button("Grid")
        gridButton.prefWidth = 50.0
        val sep = Separator(Orientation.VERTICAL)

        // Group 2 (hide and archive)
        val archiveLabel = Label("Show archive:")
        val myCheck = CheckBox()
        myCheck.isSelected = true;
        val sep2 = Separator(Orientation.VERTICAL)

        // Group 3 (sort order)
        val orderLabel = Label("Order by:")
        val myDrop = ChoiceBox(
            FXCollections.observableArrayList("Length (asc)", "Length (desc)"))
        myDrop.selectionModel.select(0)

        // Clear button
        val clearButton = Button("Clear")

        // Display toolbar sections
        val section1A = HBox(viewLabel)
        section1A.padding = Insets(5.0, 0.0, 5.0, 0.0)
        val section1B = HBox(archiveLabel,myCheck)
        section1B.spacing = 10.0
        section1B.padding = Insets(5.0, 0.0, 5.0, 0.0)
        val section1C = HBox(orderLabel)
        section1C.padding = Insets(5.0, 0.0, 5.0, 0.0)
        val section1 = HBox(section1A,listButton,gridButton,sep,
            section1B, sep2,section1C,myDrop)
        section1.spacing = 10.0
        val section2 = HBox()
        HBox.setHgrow(section2, Priority.ALWAYS)
        val section3 = HBox(clearButton)
        toolbar.items.addAll(section1, section2,section3)
        toolbar.padding = Insets(10.0,10.0,10.0,10.0)

        // Create button
        val createButton = Button("Create")
        createButton.prefWidth = 75.0
        createButton.prefHeight = 42.0
        createButton.padding = Insets(10.0)


        // Sisual for list format
        val myText = TextArea()
        HBox.setHgrow(myText, Priority.ALWAYS)
        val inputBox = HBox(myText, createButton)
        HBox.setHgrow(inputBox, Priority.ALWAYS)
        inputBox.background = Background(BackgroundFill(Color.LIGHTSALMON, CornerRadii(10.0), null))
        inputBox.padding = Insets(10.0)
        inputBox.spacing = 10.0
        inputBox.prefHeight = 62.0
        val placeHolderbox = VBox(inputBox)
        myText.prefHeight = 62.0
        placeHolderbox.padding = Insets(10.0)
        val allNotes = mutableListOf<CreateNote>()
        val nonArcNotesList = mutableListOf<CreateNote>()

        //creating original 4 notes
        for(i in 0 until 2) {
            var noteOne = CreateNote("this note is the first one")
            HBox.setHgrow(noteOne.noteBox, Priority.ALWAYS)
            allNotes.add(noteOne)
            nonArcNotesList.add(noteOne)
            placeHolderbox.children.add(displayingNotes(noteOne))
            placeHolderbox.spacing = 10.0
        }
        for(i in 0 until 2) {
            var noteOne = CreateNote("this note is not the first one and it is archived , so you can def tell that hard")
            noteOne.checkArchive.isSelected = true
            allNotes.add(noteOne)
            HBox.setHgrow(noteOne.noteBox, Priority.ALWAYS)
            placeHolderbox.children.add(displayingNotes(noteOne))
            placeHolderbox.spacing = 10.0
        }

        // Creating original status bar
        val tempLabel1 = Label("4")
        val tempLabel2 = Label(" notes,")
        val tempLabel3 = Label(" 2")
        val tempLabel4 = Label(" of which are active")
        var showcase = HBox(tempLabel1, tempLabel2, tempLabel3, tempLabel4)


        // Checking when create button is pressed
        createButton.onAction= EventHandler{
            // For status bar
            if(showcase.children.isNotEmpty()) {
                showcase.children.remove(0, 4)
            }

            // Delete previous children
            if(placeHolderbox.children.isNotEmpty()) {
                if (placeHolderbox.children.count() == 1) {
                    placeHolderbox.children.remove(1, 1)
                } else {
                    placeHolderbox.children.remove(1, totalNotes + 1)
                }
            }

            // create new notes
            val currentNote = CreateNote(myText.text)
            HBox.setHgrow(currentNote.noteBox, Priority.ALWAYS)
            allNotes.add(currentNote)

            // Build all notes
            allNotes.forEach(){
                placeHolderbox.children.add(displayingNotes(it))
            }

            totalNotes += 1
            nonArcNotes += 1
            placeHolderbox.spacing = 10.0

            //Display status bar updates
            var tValue = Label(""+totalNotes)
            var displayNote = Label(" note, ")
            if(totalNotes != 1){
                displayNote = Label(" notes, ")
            }
            var aValue = Label(""+ nonArcNotes)
            var dNote = Label(" of which is active")
            if(nonArcNotes != 1){
                dNote = Label(" of which are active")
            }
            showcase.children.addAll(tValue,displayNote,aValue,dNote)

        }


        /*myCheck.selectedProperty().addListener { _, _, newValue ->
            if (newValue) {
                if(placeHolderbox.children.isNotEmpty()) {
                    if (placeHolderbox.children.count() == 1) {
                        placeHolderbox.children.remove(1, 1)
                    } else {
                        placeHolderbox.children.remove(1, nonArcNotes + 1)
                    }
                }
                allNotes.forEach() {
                    placeHolderbox.children.add(displayingNotes(it))
                }
            } else {
                if(placeHolderbox.children.isNotEmpty()) {
                    if (placeHolderbox.children.count() == 1) {
                        placeHolderbox.children.remove(1, 1)
                    } else {
                        println(totalNotes)
                        println(nonArcNotes)
                        placeHolderbox.children.remove(1, totalNotes + 1)
                    }
                }
                nonArcNotesList.forEach() {
                    placeHolderbox.children.add(displayingNotes(it))
                }

            }
        }*/


        // Sort current notes based on selection
        myDrop.selectionModel.selectedItemProperty().addListener(){
            _,_,newValue ->
            if(newValue == "Length (asc)") {
                if (placeHolderbox.children.isNotEmpty()) {
                    if (placeHolderbox.children.count() == 1) {
                        placeHolderbox.children.remove(1, 1)
                    } else {
                        placeHolderbox.children.remove(1, totalNotes + 1 )
                    }
                }
                allNotes.sortBy { it.inputText.length }
                allNotes.forEach() {
                    placeHolderbox.children.add(displayingNotes(it))
                }

            } else {
                if(placeHolderbox.children.isNotEmpty()) {
                    if (placeHolderbox.children.count() == 1) {
                        placeHolderbox.children.remove(1, 1)
                    } else {
                        placeHolderbox.children.remove(1, totalNotes + 1)
                    }
                }
                allNotes.sortByDescending {it.inputText.length }
                allNotes.forEach(){
                    placeHolderbox.children.add(displayingNotes(it))
                }
            }
        }

        // Button that is clicked is greyed out
        listButton.onAction = EventHandler {
            listButton.isDisable = true;
            gridButton.isDisable = false;
        }
        gridButton.onAction = EventHandler {
            listButton.isDisable = false;
            gridButton.isDisable = true;
        }

        // When the clear button is clicked all, notes disappear
        clearButton.onAction= EventHandler {
            if(placeHolderbox.children.isNotEmpty()) {
                if (placeHolderbox.children.count() == 1) {
                    placeHolderbox.children.remove(1, 1)
                } else {
                    placeHolderbox.children.remove(1, totalNotes + 1)
                }
            }
            if(showcase.children.isNotEmpty()) {
                showcase.children.remove(0, 4)
            }
            totalNotes = 0;
            nonArcNotes = 0;
            allNotes.clear()
            val tempLabel1 = Label("0")
            val tempLabel2 = Label(" notes,")
            val tempLabel3 = Label(" 0")
            val tempLabel4 = Label(" of which are active")
            showcase.children.addAll(tempLabel1, tempLabel2, tempLabel3, tempLabel4)
        }

        //add a scroller if not enough space
        val displayPane = ScrollPane(placeHolderbox).apply {
            isFitToWidth = true
            hbarPolicy = ScrollPane.ScrollBarPolicy.AS_NEEDED
        }


        // Display program all together
        showcase.padding = Insets(5.0,5.0,5.0,5.0)
        val root = BorderPane()
        root.top= toolbar
        root.center = displayPane
        root.bottom = showcase

        stage.title = "CS349 - A1 Notes - slev"
        stage.scene = Scene(root, 800.0, 600.0)
        stage.show()
        stage.apply { minWidth = 640.0; minHeight = 480.0 }

    }

}

fun displayingNotes(note : CreateNote ) : HBox{
    val currentHBox = HBox(note.noteBox, note.cBox)
    currentHBox.padding = Insets(10.0)
    if(note.checkArchive.isSelected ) {
        currentHBox.background = Background(BackgroundFill(Color.LIGHTGREY, CornerRadii(10.0), null))
    } else {
        currentHBox.background = Background(BackgroundFill(Color.LIGHTYELLOW, CornerRadii(10.0), null))
    }
    note.checkArchive.selectedProperty().addListener{
            _,_,newValue ->
        if(newValue) {
            currentHBox.background = Background(BackgroundFill(Color.LIGHTGREY, CornerRadii(10.0), null))
        }else{
            currentHBox.background = Background(BackgroundFill(Color.LIGHTYELLOW, CornerRadii(10.0), null))

        }
    }
    HBox.setHgrow(currentHBox, Priority.ALWAYS)
    currentHBox.spacing = 10.0
    return currentHBox
}
class CreateNote(val inputText: String){
    var note = Label(inputText)
    val noteBox = HBox(note)
    val archivedLabel = Label("Archived")
    val checkArchive = CheckBox()
    val cBox = HBox(checkArchive, archivedLabel)
    //val nBox = HBox(noteBox, cBox)
    init{
        note.isWrapText = true
        HBox.setHgrow(noteBox, Priority.ALWAYS)
        cBox.padding = Insets(10.0)
        cBox.spacing = 10.0
    }
}