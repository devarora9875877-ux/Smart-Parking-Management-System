# Smart Parking Management System

## Member 2 · Parking Management · Evaluation 1

The console application starts with two floors. Each floor has three car slots and three bike slots. You can register a vehicle, park it, look up its active ticket, record its exit, and see the current occupancy.

`src` contains Member 2’s six Java files. The program uses the `Vehicle`, `Car`, and `Bike` classes from Member 1. While `VehicleRegistry` is pending, `Main` keeps registrations in a temporary `ArrayList<Vehicle>`. Member 3 will use the completed ticket for billing and history. All data for this evaluation lives in memory and resets when the program closes.

## How the classes work

### 1. `ParkingSlot`

One `ParkingSlot` object represents one space in the lot.

| Variable | Meaning |
| --- | --- |
| `slotId` | Slot label, such as `F1-01`. |
| `slotType` | The kind of vehicle the slot accepts, `Car` or `Bike`. |
| `parkedVehicle` | The `Vehicle` object in the slot. `null` means the slot is empty. |

| Method | What it does |
| --- | --- |
| `ParkingSlot(slotId, slotType)` | Sets the slot ID and type and starts the slot empty. |
| `getSlotId()`, `getSlotType()`, `getParkedVehicle()` | Read the private fields. |
| `isAvailable()` | Returns `true` when `parkedVehicle` is `null`. |
| `parkVehicle(vehicle)` | Returns `false` for a missing vehicle, an occupied slot, or a type mismatch. Otherwise it stores the vehicle and returns `true`. |
| `removeVehicle()` | Sets `parkedVehicle` back to `null`, freeing the space. |
| `displaySlotDetails()` | Prints the slot ID, type, and availability. Occupied slots also show the vehicle’s registration number. |

### 2. `ParkingFloor`

One `ParkingFloor` object contains all six spaces on one level.

| Variable | Meaning |
| --- | --- |
| `floorNumber` | The floor number used in the display and in slot IDs. |
| `slots` | A `ParkingSlot[2][3]` array. Row `0` holds cars and row `1` holds bikes. |
| `row`, `column` | Array indexes used by the nested `for` loops. |
| `slotNumber` | Counts from 1 through 6 while the constructor creates slots. |
| `availableCount`, `totalCount` | Counters used to calculate availability and occupancy. |

| Method | What it does |
| --- | --- |
| `ParkingFloor(floorNumber)` | Creates the 2D array and fills each cell with one slot. Floor 1 gets IDs `F1-01` through `F1-06`. |
| `getFloorNumber()`, `getSlots()` | Read the floor number and provide access to its slots. |
| `findAvailableSlot(vehicleType)` | Walks the array row by row and returns the first empty matching slot, or `null`. |
| `getAvailableSlotCount()` | Counts slots for which `isAvailable()` is `true`. |
| `getOccupiedSlotCount()` | Subtracts available slots from the total number of cells. |
| `displayFloorDetails()` | Prints the floor number and calls `displaySlotDetails()` for each slot. |

### 3. `ParkingLot`

The `ParkingLot` object groups all floors and calculates totals for the whole lot.

| Variable | Meaning |
| --- | --- |
| `parkingLotName` | The name printed at the top of the status display. |
| `floors` | An `ArrayList<ParkingFloor>` that stores floors in the order they were added. |
| `availableCount`, `occupiedCount` | Running totals as the code visits each floor. |

| Method | What it does |
| --- | --- |
| `ParkingLot(parkingLotName)` | Sets the lot name and creates an empty floor list. |
| `addFloor(floor)` | Checks the floor number against the existing list and adds the floor only if its number is new. A successful add returns `true`. |
| `getFloors()` | Returns a copy of the list, so adding to that copy leaves the lot’s list alone. |
| `findAvailableSlot(vehicleType)` | Asks each floor for a suitable slot in order and returns the first result. If every floor returns `null`, the lot returns `null` too. |
| `getTotalSlots()` | Adds available and occupied slots. |
| `getAvailableSlots()`, `getOccupiedSlots()` | Sum the corresponding counts from each floor. |
| `displayParkingStatus()` | Prints every floor’s slots followed by the three capacity totals. |

### 4. `ParkingTicket`

A `ParkingTicket` represents one visit to the lot. Parking the same vehicle again after exit creates a new ticket.

| Variable | Meaning |
| --- | --- |
| `ticketId` | The visit number, such as `T1`. |
| `vehicle` | The vehicle this ticket belongs to. |
| `parkingSlot` | The slot assigned to this visit. |
| `entryTime`, `exitTime` | `LocalDateTime` values for when the vehicle arrived and left. `exitTime` starts as `null`. |
| `endTime` | The recorded exit time for a completed visit, or the current time for an active visit. |

| Method | What it does |
| --- | --- |
| `ParkingTicket(ticketId, vehicle, parkingSlot, entryTime)` | Stores the session details and leaves the exit time unset. |
| `getTicketId()`, `getVehicle()`, `getParkingSlot()`, `getEntryTime()`, `getExitTime()` | Read the ticket details. |
| `setExitTime(exitTime)` | Records the exit timestamp when the manager processes the exit. |
| `getParkingDurationMinutes()` | Uses `Duration.between(entryTime, endTime).toMinutes()` to return whole elapsed minutes as a `long`. A 30-second stay displays as `0` minutes. |
| `displayTicketDetails()` | Prints details from the ticket’s own `vehicle` field, plus the slot ID and times. This keeps the vehicle details correct after the slot is reassigned. |

### 5. `ParkingManager`

The manager connects the parking layout with the list of tickets for vehicles currently parked.

| Variable | Meaning |
| --- | --- |
| `parkingLot` | The lot the manager uses to find and display slots. |
| `activeTickets` | An `ArrayList<ParkingTicket>` containing current visits only. |
| `ticketCounter` | Starts at 1 and increases after each successful entry. |
| `ticketIndex` | The position of a matched ticket in `activeTickets`. `-1` means the search found no match. |
| `searchNumber` | The registration number after removing leading and trailing spaces. |

| Method | What it does |
| --- | --- |
| `ParkingManager(parkingLot)` | Stores the lot, starts the empty ticket list, and initializes the counter. |
| `parkVehicle(vehicle)` | Validates the details, rejects duplicate parking, finds a compatible slot, records entry time, and adds and returns the new ticket. A failed entry returns `null`. |
| `exitVehicle(registrationNumber)` | Finds the ticket, records exit time, frees the slot, removes the ticket from the active list, and returns the completed ticket. Returns `null` if the vehicle is absent. |
| `searchParkedVehicle(registrationNumber)` | Returns the active ticket matching the registration number, or `null`. |
| `findTicketIndex(registrationNumber)` | Shares the same indexed `for` loop between search and exit. It compares numbers with `equalsIgnoreCase()`. |
| `isBlank(text)` | Checks whether a value is `null`, empty, or made up of spaces. |
| `displayActiveTickets()`, `displayParkingStatus()` | Print the current tickets and pass the request to the lot for the status display, respectively. |

### 6. `Main`

`Main` starts the console application and turns menu choices into calls to the other classes.

| Variable | Meaning |
| --- | --- |
| `parkingLot`, `manager` | The single lot and the manager that handles parking and tickets. |
| `registeredVehicles` | Temporary list of all registered vehicles. A vehicle stays registered after it exits. |
| `scanner` | The single `Scanner` for all console input. |
| `running`, `choice` | The menu loop flag and the selected menu number. Option 8 sets `running` to `false`. |
| `type`, `registrationNumber`, `brand`, `vehicleName` | Registration input. Brand is the maker, such as Toyota. Vehicle name is the model, such as Fortuner. |

| Method | What it does |
| --- | --- |
| `main(args)` | Creates the lot, adds floors 1 and 2, creates the manager and scanner, and starts the menu. It handles the input stream ending and then closes the scanner. |
| `runMenu()` | Uses a `while` loop and a traditional `switch`. It catches `NumberFormatException` for blank, nonnumeric, or oversized numeric input. The `default` case handles numbers outside 1–8. |
| `displayMenu()` | Prints the eight options. |
| `registerVehicle()` | Reads and validates the form, prevents duplicate registration numbers, and creates a `Car` or `Bike` through Member 1’s constructor. |
| `displayRegisteredVehicles()` | Loops over the registration list and displays each vehicle. |
| `findRegisteredVehicle()` | Searches that list with `equalsIgnoreCase()`. |
| `parkRegisteredVehicle()` | Resolves a registration to a `Vehicle`, handles an already parked vehicle, then calls the manager. |
| `exitParkedVehicle()`, `searchParkedVehicle()` | Read a registration number, call the manager, and display the returned ticket or a message. |
| `readText()` | Prints a prompt, reads a complete line, and trims surrounding whitespace. This also lets the user enter a model name with spaces. |

## How parking allocation works

1. `Main` looks up the registered vehicle and passes the `Vehicle` object to `ParkingManager`.
2. The manager checks that the registration number, brand, and model are present, that the type is `Car` or `Bike`, and that the vehicle has no active ticket.
3. `ParkingLot` visits the floors in the order they were added. `ParkingFloor` uses nested loops to scan each row and column. A slot qualifies when it is empty and its type matches the vehicle.
4. The search returns the first qualifying `ParkingSlot`. The slot checks compatibility and availability again before storing the vehicle.
5. The manager creates a `ParkingTicket`, records the current time, and adds the ticket to its active list. The ticket links the original vehicle with the assigned slot.
6. On exit, the manager records the exit time, clears the slot, and removes the ticket from the active list. The completed ticket still contains the details of the original visit.

For example, the first three cars get `F1-01`, `F1-02`, and `F1-03`. The fourth gets `F2-01`. If all six car slots are occupied, another car is refused even if a bike space is available.

The object relationships are `Main → ParkingManager → ParkingLot → ParkingFloor → ParkingSlot`. `ParkingManager` also stores the active `ParkingTicket` objects. Each ticket holds its own `Vehicle` reference and the assigned `ParkingSlot` reference.

## Compile and run in VS Code

1. Install a JDK, such as JDK 21. Check that `javac -version` and `java -version` run in your terminal.
2. Extract the project and open the **SmartParkingManagementSystem** folder in VS Code using **File > Open Folder**.
3. Put Member 1’s `Vehicle.java`, `Car.java`, and `Bike.java` directly in `src` beside the six supplied files. These three files are required for this version to compile. All files use the default package.
4. Open **Terminal > New Terminal** in VS Code. From the project folder, run these commands. Create `out` once.

```sh
mkdir out
javac -d out -sourcepath src src/Main.java
java -cp out Main
```

`javac` finds the classes referenced by `Main` in `src` and puts the compiled `.class` files in `out`. Run the last two commands again after editing a source file. In the menu, use option 1 to register a vehicle before using option 3 to park it.

## Connect the other members’ code

### Member 1 · Vehicle registry

1. Confirm the actual registry constructor, method signatures, return values, and duplicate-registration behavior with Member 1. The proposed calls are `registerVehicle(Vehicle vehicle)`, `searchVehicle(String registrationNumber)`, and `displayAllVehicles()`.
2. In `Main`, replace `registeredVehicles` with the team’s `VehicleRegistry` object and pass it into the menu methods that need it. Send newly created `Car` and `Bike` objects to `registerVehicle`. Keep the existing form input and use the registry’s result to report success or a duplicate.
3. Use `displayAllVehicles()` for option 2 and `searchVehicle(registrationNumber)` to obtain the `Vehicle` for option 3. Remove the temporary list and its list-specific lookup and display methods after switching to the registry.

### Member 3 · Billing and history

1. Confirm the signatures for `Billing.calculateFee(ParkingTicket ticket)` and `ParkingHistory.addRecord(ParkingTicket ticket)`, including constructors, static versus instance methods, and return types.
2. In `Main.exitParkedVehicle()`, use the returned ticket inside the success branch. Pass that ticket to the confirmed billing and history methods and display the fee as required by the team.
3. Recompile with the team’s source files and run a complete registration → parking → exit → billing/history scenario. Keep the team’s entry point in this single `Main.java`.

## Sample console output

Excerpt from the console test. Repeated menus are omitted. Prompts show what you would type in an interactive terminal. The details formatting comes from `Vehicle.displayDetails()` and may vary with Member 1’s implementation.

```text
====================================
SMART PARKING MANAGEMENT SYSTEM
====================================
1. Register Vehicle
2. Display Registered Vehicles
3. Park Vehicle
4. Exit Vehicle
5. Display Parking Status
6. Search Parked Vehicle
7. Display Active Parking Tickets
8. Exit Application
Enter your choice: 1
Enter vehicle type (Car or Bike): Car
Enter registration number: PB01AB1234
Enter brand: Toyota
Enter vehicle name (model): Fortuner
Vehicle registered successfully.

Enter your choice: 3
Enter registration number: pb01ab1234
Vehicle parked successfully.

Ticket ID: T1
Registration number: PB01AB1234
Brand: Toyota
Vehicle name: Fortuner
Vehicle type: Car
Parking slot: F1-01
Entry time: 2026-11-24T15:24:49.740694
Exit time: Vehicle still parked
Parking duration: 0 minute(s)

Enter your choice: 4
Enter registration number: pb01ab1234
Vehicle exited successfully.

Ticket ID: T1
Registration number: PB01AB1234
Brand: Toyota
Vehicle name: Fortuner
Vehicle type: Car
Parking slot: F1-01
Entry time: 2026-11-24T15:24:49.740694
Exit time: 2026-11-24T15:24:49.741674
Parking duration: 0 minute(s)
```

## Test results

Compiled all six classes with JDK 21 and temporary test definitions matching the supplied `Vehicle`, `Car`, and `Bike` signatures. Ran console scenarios with entered values and used a small temporary Java driver for checks that the menu cannot trigger directly. The test files were kept outside the project. Compilation, console runs, and class-level checks passed.

| # | Test | Observed result |
| --- | --- | --- |
| 1 | Park a car | `PB01AB1234` received `T1` in `F1-01`. |
| 2 | Park a bike | `PB01CD5678` received `T2` in `F1-04`. |
| 3 | Park the same vehicle twice | The second attempt was rejected, including a case-insensitive match. |
| 4 | Try a car in a bike slot | The slot rejected it and stayed available. A car was also refused after all car slots filled while bike slots remained. |
| 5 | Fill the first floor’s car slots | The fourth car was assigned `F2-01`. The fourth bike was assigned `F2-04` in the capacity run. |
| 6 | Fill the lot | The display showed 0 available and 12 occupied. Additional cars and bikes were rejected. |
| 7 | Exit a parked vehicle | The original ticket was returned with an exit time and removed from the active list. |
| 8 | Check the released slot | `F1-01` became available and was reused by a later entry. The completed ticket still showed its original vehicle. |
| 9 | Search for a parked vehicle | A lower-case registration lookup returned the correct ticket. |
| 10 | Search for or exit an absent vehicle | The manager returned `null`. The menu printed `Vehicle is not parked.` |
| 11 | Display slot counts | Starting counts were 12 available and 0 occupied. After two entries and one exit, they were 11 available and 1 occupied. |
| 12 | Check entry, exit, and duration | Entry was recorded with exit initially unset. Exit was recorded on departure. A test duration of 90 minutes and 30 seconds returned 90 minutes. |
| 13 | Enter invalid menu choices | `oops`, `9`, and `2147483648` produced an error and the menu continued. Ending input during registration also exited cleanly. |

Additional checks passed for duplicate floors, blank registration details, an unsupported registration type, parking an unregistered vehicle, and trying to overwrite an occupied slot.

**Pending team verification:** Compile with Member 1’s actual `Vehicle`, `Car`, and `Bike` files, then verify the final registry lookup and display, fee calculation, and history recording. `VehicleRegistry`, `Billing`, and `ParkingHistory` were unavailable for these integration tests.

## Expected viva questions

| Question | Easy answer |
| --- | --- |
| 1. What is the difference between a class and an object here? | `ParkingSlot` is the blueprint. The space `F1-01` is an object created from that class. |
| 2. Why are fields private? | Encapsulation lets the class control how the fields are read and changed. For example, a vehicle enters a slot through `parkVehicle()`, where validation happens. |
| 3. What does `this.slotId = slotId` do? | The left side is the current object’s field. The right side is the value received by the constructor. |
| 4. Why use a 2D array for slots? | Each floor has a fixed layout of two rows and three columns. The rows represent car and bike spaces. |
| 5. Why use an `ArrayList` for tickets? | The number of parked vehicles changes, so tickets can be added and removed as vehicles arrive and leave. |
| 6. Why use `null` for an empty slot? | The slot has no `Vehicle` reference while empty. One field represents both the parked vehicle and whether the space is taken. |
| 7. How does the program choose a slot? | It scans floors in order, then scans each floor row by row, returning the first empty slot with a matching type. |
| 8. What happens when the car spaces are full and a bike slot is open? | The car cannot use the bike slot. The search returns `null` and the menu prints that no car slot is available. |
| 9. How does the code prevent duplicate parking? | It searches the active ticket list for the same registration number before assigning a slot. |
| 10. Why use `equalsIgnoreCase()`? | It compares the contents of two registration strings while treating uppercase and lowercase letters as equal. |
| 11. What happens when a vehicle exits? | The manager sets the ticket’s exit time, frees its slot, removes the active ticket, and returns the completed ticket. |
| 12. Why does the ticket store the vehicle as well as the slot? | A later vehicle can take the same slot. The ticket’s `vehicle` field still identifies the original visit. |
| 13. How is the duration calculated for a car that is still parked? | The ticket uses the current time as the end time and computes the whole minutes since entry. |
| 14. How are ticket IDs generated? | The manager joins `T` with `ticketCounter`. The counter increases after a successful entry. |
| 15. Why can a `Vehicle` variable hold a `Car` or a `Bike`? | Both classes extend `Vehicle`. This is polymorphism, so the parking code works with either subclass through the same parent type. |
| 16. Where is method overriding used? | `Car` and `Bike` each implement `Vehicle.getVehicleType()` with their own return value. |
| 17. Where is composition used? | A floor contains six slot objects. A lot groups floor objects. |
| 18. What happens when the user types letters for the menu choice? | `Integer.parseInt()` throws `NumberFormatException`. The catch block shows an error and the loop asks again. |
| 19. What does `break` do in the menu? | It ends the current `switch` case. The `while` loop continues unless option 8 has set `running` to `false`. |
| 20. What happens to the data when the program closes? | The lists and objects are in memory, so the next run starts empty. Persistent storage is planned for Evaluation 2. |
