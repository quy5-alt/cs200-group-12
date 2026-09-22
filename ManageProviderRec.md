**Manage Provider Records**

Use Case: Manage Provider Records 
Context: Lets a ChocAn Operator add, update, or delete provider records at the Data Center. 
Actors: ChocAn Operator

**Main Success Scenario:**

Operator selects "Manage Provider Records" from the Data Center menu.
System displays the options: Add Provider, Update Provider, Delete Provider, Exit.
Operator selects an option. 3.1. System performs the corresponding extension use case.
System displays a confirmation message and returns to step 2.
Operator selects Exit.
System returns to the Data Center main menu.

**Extensions:**
3a. Operator selects Add Provider: system executes Add Provider Record. 
3b. Operator selects Update Provider: system executes Update Provider Record. 
3c. Operator selects Delete Provider: system executes Delete Provider Record. 
3d. Operator enters an invalid option: system displays an error message and returns to step 2.
