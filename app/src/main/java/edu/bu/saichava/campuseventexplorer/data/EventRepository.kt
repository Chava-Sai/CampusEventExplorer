package edu.bu.saichava.campuseventexplorer.data

/**
 * The application's event collection.
 *
 * This is the single source of truth that both destinations read from: the list destination shows
 * it, and the detail destination looks an event up in it by the id carried on the route.
 */
object EventRepository {

    val events: List<CampusEvent> = listOf(
        CampusEvent(
            id = 1L,
            title = "Mobile Development Meetup",
            category = "Technology",
            location = "CAS 214",
            time = "Tuesday 6:30 PM",
            description = "A hands-on session on building Android apps with Jetpack Compose. " +
                "Bring a laptop with Android Studio installed; we will work through declarative " +
                "layouts, state hoisting and type-safe navigation together. Pizza is provided and " +
                "no prior Compose experience is expected."
        ),
        CampusEvent(
            id = 2L,
            title = "Undergraduate Research Symposium",
            category = "Academic",
            location = "GSU Metcalf Hall",
            time = "Wednesday 10:00 AM",
            description = "Undergraduates from every college present posters on a semester of " +
                "research, from computational biology to urban policy. Faculty judges award three " +
                "prizes at noon, and the session is open to anyone curious about getting started " +
                "in a lab."
        ),
        CampusEvent(
            id = 3L,
            title = "Terriers vs. Eagles Hockey",
            category = "Athletics",
            location = "Agganis Arena",
            time = "Friday 7:00 PM",
            description = "The rivalry game of the season. Student tickets are free with a valid " +
                "ID at the Agganis box office, but the student section fills quickly, so arrive by " +
                "6:15 PM. The pep band plays from the first intermission onward."
        ),
        CampusEvent(
            id = 4L,
            title = "Jazz Ensemble Night",
            category = "Music",
            location = "Tsai Performance Center",
            time = "Saturday 8:00 PM",
            description = "The student jazz ensemble closes the fall season with a program of " +
                "Mingus, Shorter and three original student compositions. Doors open at 7:30 PM " +
                "and seating is general admission."
        ),
        CampusEvent(
            id = 5L,
            title = "Career Fair: Software & Data",
            category = "Career",
            location = "Questrom Auditorium",
            time = "Thursday 1:00 PM",
            description = "Over forty employers hiring for summer internships and new-grad " +
                "software, data and product roles. Bring printed resumes; a resume review table " +
                "runs alongside the fair from 1:00 PM to 3:00 PM."
        ),
        CampusEvent(
            id = 6L,
            title = "Sunrise Yoga on the Esplanade",
            category = "Wellness",
            location = "BU Beach",
            time = "Monday 7:00 AM",
            description = "A free all-levels flow led by a certified instructor from FitRec. Mats " +
                "are available on a first-come basis, and the class moves indoors to FitRec " +
                "Studio B if it rains."
        ),
        CampusEvent(
            id = 7L,
            title = "International Food Festival",
            category = "Community",
            location = "Marsh Plaza",
            time = "Sunday 12:00 PM",
            description = "Twenty student cultural organizations cook and serve dishes from their " +
                "home countries. Tickets are five tasting tokens for a dollar, and all proceeds go " +
                "to the student emergency fund."
        ),
        CampusEvent(
            id = 8L,
            title = "Robotics Club Open Build",
            category = "Technology",
            location = "ENG Building Lab 103",
            time = "Wednesday 5:00 PM",
            description = "A weekly open lab where the competition team prototypes the drivetrain " +
                "for this year's robot. Drop in to solder, machine parts or just watch; safety " +
                "glasses are supplied at the door."
        ),
        CampusEvent(
            id = 9L,
            title = "Guest Lecture: Ethics of AI",
            category = "Academic",
            location = "Photonics Center 206",
            time = "Thursday 4:00 PM",
            description = "A visiting professor of philosophy and computer science on how " +
                "accountability should be assigned when automated systems cause harm. A " +
                "moderated question period follows the forty-minute talk."
        ),
        CampusEvent(
            id = 10L,
            title = "Film Screening: Rear Window",
            category = "Arts",
            location = "COM 101",
            time = "Friday 9:00 PM",
            description = "A 35mm screening of Hitchcock's 1954 thriller, introduced by a film " +
                "studies doctoral candidate. Free popcorn, and a short discussion follows for " +
                "anyone who wants to stay."
        )
    )
}
