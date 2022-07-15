<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.sydney.edu.au/SOFT2412-2021S2/w14_c3_group5_A2/">
  </a>

  <h1 align="center">W14 C3 Group 5 HOYTSOFT Movies</h1>

  <p align="center">
    USYD SOFT2412 Semester 2, 2021
  </p>
</p>

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary><h2 style="display: inline-block">Table of Contents</h2></summary>
  <ol>
    <li><a href="#description">Description</a></li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#prerequisites">Prerequisites</a></li>
    <li><a href="#running">Running</a></li>
    <li><a href="#testing">Testing</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contribute">Contribute</a></li>
  </ol>
</details>

<!-- Description -->
## Description

This program is a ticket booking management system designed to allow customers to book their choice of movie online, therefore reducing potential waiting time while at a cinema. Users can view movie details as Guests (without needing to login), alternatively they can register for an account and login to book tickets. Both Guests and Customers can view movie details as well as filter and query for their desired movie screening. They can also pick screen sizes and seats, however only logged-in Customers can proceed to checkout. Cinema staff and managers have the ability to insert, delete and modify movie data, as well as add new shows for the upcoming week. Managers have the additional ability to modify staff member details. Both of these roles are able to obtain movie, booking and cancellation reports.

<!-- RUNNING -->
## Running
To compile the program, run `gradle clean build` then `gradle jar`.

After that, run `cd app`, followed by `java -jar build/libs/app.jar`.

<!-- USAGE -->
## Usage

Program usage is divided between four user groups: Guest Users, Customers, Cinema Staff and Manager Roles

#### Guest User

1. User prompted to log in, register for an account, browse as a guest or exit the program.
2. Guest users can browse movie options by entering ‘3’. Proceed to step 3.
  + Alternatively, they can register for an account by entering '2'. Proceed to step 8.
3. User is shown a list of basic movie details, including cinema locations, screen sizes and showing times. User can choose to select a movie for more details (M), filter by cinema location (L), filter by cinema screen size (S), or clear filters (C). User can also enter a number to jump to that page.
  + If user enters ‘M’, proceed to step 7.
  + If user enters ‘L’, proceed to step 4.
  + If user enters ‘S’, proceed to step 5.
  + If user enters ‘C’, proceed to step 6.
  + If user enters a page number, page will be shown and prompt is repeated.
  + If user enters ‘cancel’, go back to step 1.
4. User is prompted to enter a cinema location from a list, or ‘cancel’ to go back.
  + If user enters an invalid cinema location, error message is shown and user goes back to step 3.
  + If user enters a valid cinema location, movies showing at that cinema location will be shown and user goes back to step 3.
  + If user enters 'cancel', go back to step 3.
5. User is prompted to enter a cinema screen size from a list, or ‘cancel’ to go back.
  + If user enters an invalid cinema screen size, error message is shown and user goes back to step 3.
  + If user enters a valid cinema screen size, movies showing with that screen size will be shown and user goes back to step 3.
  + If user enters 'cancel', go back to step 3.
6. All cinema location and/or cinema screen size filters will be reset, all movies will be shown and user goes back to step 3.
7. User is prompted to enter a movie option number, or ‘cancel’ to go back.
 is prompted to enter a cinema location from a list.
  + If user enters a valid movie option, full movie details will be displayed and user is prompted to login to proceed to booking (implementation to be adjusted for sprint 2 to allow Guests to book tickets until payment stage). User goes back to step 3.
  + If user enters an invalid movie option, error message is displayed and user goes back to step 3.
  + If user enters 'cancel', go back to step 3.
8. User is prompted to select the type of ticket they would like to book from a list of options, or 'cancel' to go back.
  + If user enters a valid ticket type, user is prompted to enter the number of tickets of that type that they would like to purchase. Prompt is repeated.
  + If user enters an invalid ticket type, error message is displayed and prompt is repeated.
  + If user enters ‘cancel’, go back to step 3.
  + If user enters ‘F’, this indicates that they are finished adding the tickets they would like to purchase and can proceed to step 9.
9. User is prompted to choose their desired seat allocation (Front, Middle or Rear), or 'cancel' to go back.
  + If user enters an invalid seat allocation, error message is displayed and prompt is repeated.
  + If user enters 'cancel', go back to step 3.
10. User is prompted to enter the number of tickets they would like to allocate to the chosen section, or 'cancel' to go back.
  + If user enters more tickets than they have booked, error message is shown and prompt is repeated.
  + If user still has more tickets to allocate seats to, go back to step 9.
  + If user enters 'cancel', go back to step 3.
11. User is prompted to either register or login to proceed to payment, or enter 'cancel' to go back.
  + Once user logs in successfully, proceed to step 11 of Customer usage.
  + If user enters 'cancel', go back to step 3.

#### Customer

1. User prompted to log in, register for an account, browse as a guest or exit the program.
2. If user has an existing account, they may choose to log in.
  + If their login details are correct, proceed to step 3.
  + If their login details are incorrect, error message is shown and user goes back to step 1.
3. User is shown a list of basic movie details, including cinema locations, screen sizes and showing times. User can choose to select a movie for more details (M), filter by cinema location (L), filter by cinema screen size (S), or clear filters (C). User can also enter a number to jump to that page.
  + If user enters ‘M’, proceed to step 7.
  + If user enters ‘L’, proceed to step 4.
  + If user enters ‘S’, proceed to step 5.
  + If user enters ‘C’, proceed to step 6.
  + If user enters a page number, page will be shown and prompt is repeated.
  + If user enters ‘cancel’, go back to step 1.
4. User is prompted to enter a cinema location from a list, or 'cancel' to go back.
  + If user enters an invalid cinema location, error message is shown and user goes back to step 3.
  + If user enters a valid cinema location, movies showing at that cinema location will be shown and user goes back to step 3.
  + If user enters 'cancel', go back to step 3.
5. User is prompted to enter a cinema screen size from a list, or 'cancel' to go back.
  + If user enters an invalid cinema screen size, error message is shown and user goes back to step 3.
  + If user enters a valid cinema screen size, movies showing with that screen size will be shown and user goes back to step 3.
  + If user enters 'cancel', go back to step 3.
6. All cinema location and/or cinema screen size filters will be reset, all movies will be shown and user goes back to step 3.
7. User is prompted to enter a movie option number, or ‘cancel’ to go back.
 is prompted to enter a cinema location from a list.
  + If user enters a valid movie option, full movie details will be displayed. Proceed to step 8.
  + If user enters an invalid movie option, error message is displayed and user goes back to step 3.
  + If user enters 'cancel', go back to step 3.
8. User is prompted to select the type of ticket they would like to book from a list of options, or 'cancel' to go back.
  + If user enters a valid ticket type, user is prompted to enter the number of tickets of that type that they would like to purchase. Prompt is repeated.
  + If user enters an invalid ticket type, error message is displayed and prompt is repeated.
  + If user enters ‘cancel’, go back to step 3.
  + If user enters ‘F’, this indicates that they are finished adding the tickets they would like to purchase and can proceed to step 9.
9. User is prompted to choose their desired seat allocation (Front, Middle or Rear), or 'cancel' to go back.
  + If user enters an invalid seat allocation, error message is displayed and prompt is repeated.
  + If user enters 'cancel', go back to step 3.
10. User is prompted to enter the number of tickets they would like to allocate to the chosen section, or 'cancel' to go back.
  + If user enters more tickets than they have booked, error message is shown and prompt is repeated.
  + If user still has more tickets to allocate seats to, go back to step 9.
  + If user enters 'cancel', go back to step 3.
11. User is prompted to pay for their tickets with either a credit card or gift card, or 'cancel' to go back.
  + If user enters 'C', proceed to step 12.
  + If user enters 'G', proceed to step 14.
  + If user enters an invalid payment option, error message is displayed and prompt is repeated.
  + If user enters 'cancel', go back to step 3.
12. User is prompted to use saved card details.
  + If user enters 'Y', payment success message is displayed and user goes back to step 3.
  + If user enters 'N', proceed to step 13.
13. User is prompted to enter their card details.
  + If card is verified, user is prompted to save card details to their user profile.
  + Payment success message is displayed and user goes back to step 3.
14. User is prompted to enter gift card details.
  + If gift card value is insufficient for full payment, user is prompted to either continue payment with another gift card, or use a credit card.
  + Payment success message is displayed and user goes back to step 3.

#### Cinema Staff

1. User prompted to log in, register for an account, browse as a guest or exit the program.
2. Staff can login with a unique username and password.
3. Staff is shown a list of movies with their details. Staff can (A)dd (M)ovies, (D)elete movies, (M)odify movie details, (A)dd (G)iftcards, generate (M)ovie (R)eport, generate (B)ooking (R)eport, enter a number to jump to that page or abort operation.
  + If staff enters 'AM', proceed to step 4.
  + If staff enters 'M', proceed to step 5.
  + If staff enters 'DM', proceed to step 7.
  + If staff enters 'AS', proceed to step 8.
  + If staff enters 'DS', proceed to step 10.
  + If staff enters 'AG', proceed to step 12.
  + If staff enters 'MR', proceed to step 13.
  + If staff enters 'BR', proceed to step 14.
  + If staff enters a page number, page will be shown and prompt is repeated.
  + If staff enters ‘q’, go back to step 1.
4. Staff is prompted to enter new movie details.
  + Once movie is successfully added, go back to step 3.
5. Staff is prompted to enter a movie option number to modify, or 'q' to abort operation.
  + If staff enters ‘q’, go back to step 3.
6. Staff is prompted a menu of options to modify specific movie details, or 'q' to abort operation.
  + If staff enters ‘q’, go back to step 3.
7. Staff is prompted to enter a movie option number to delete, or 'q' to abort operation.
  + If staff enters ‘q’, go back to step 3.
8. Staff is prompted to enter a movie option number to add a show to, or 'q' to abort operation.
  + If staff enters ‘q’, go back to step 3.
9. Staff is prompted to enter show details to be added, or 'q' to abort operation.
  + If staff enters ‘q’, go back to step 3.
10. Staff is prompted to enter a movie option number to delete a show from, or 'q' to abort operation.
  + If staff enters ‘q’, go back to step 3.
11. Staff is prompted to enter show details to be deleted, or 'q' to abort operation.
  + If staff enters ‘q’, go back to step 3.
12. Staff is prompted to enter a gift card number.
  + Once gift card is successfully added, go back to step 3.
13. Movie report is generated as a text file (can be found in `/src`).
14. Booking report is generated as a text file (can be found in `/src`).

#### Manager Role

1. User prompted to log in, register for an account, browse as a guest or exit the program.
2. Managers can login with a unique username and password.
3. Manager is shown a list of options; '0' to enter staff menu, '1' to edit user details, '2' to generate cancellation report or '3' to logout.
  + If manager enters '0', proceed to step 4.
  + If manager enters '1', proceed to step 5.
  + If manager enters '2', proceed to step 10.
  + If manager enters '3', go back to step 1.
4. Proceed to step 3 for Cinema Staff.
5. Manager is shown a list of users with their details. Manager can (A)dd a new user, (S)elect a user to modify or remove, enter a number to jump to that page or abort operation.
  + If manager enters 'A', proceed to step 6.
  + If manager enters 'S', proceed to step 8.
  + If manager enters a page number, page will be shown and prompt is repeated.
  + If manager enters ‘q’, go back to step 3.
6. Manager is prompted to enter the type of user to add, or 'q' to abort operation.
  + If manager enters ‘q’, go back to step 5.
7. Manager is prompted to enter a username and password.
  + Registration success message is displayed and manager goes back to step 5.
  + If manager enters ‘cancel’, go back to step 5.
8. Manager is prompted to select a user to modify, or 'q' to abort operation.
  + If manager enters ‘q’, go back to step 5.
9. Manager is prompted to select option to modify or delete the selected user, or 'q' to abort operation.
  + If manager enters ‘q’, go back to step 5.
10. Cancellation report is generated as a text file (can be found in `/src`).


#### Program clarifications

* Program clarifications added here

<!-- PREREQUISITES -->
## Prerequisites
The following specs were used in the development of this program:

Java - Version 1.8.0

Gradle (and JaCoCo) - Version 7.2

Jenkins - Version 2.309

<!-- TESTING -->
## Testing

To test the program, run `gradle clean test`. 

Jacoco test reports can be found under `/build/reports/jacoco/test/html/`.

<!-- ROADMAP -->
## Roadmap

See the [open issues](https://github.sydney.edu.au/SOFT2412-2021S2/w14_c3_group5_A2/issues) for a list of proposed features (and known issues).

<!-- CONTRIBUTE -->
## Contribute

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request
