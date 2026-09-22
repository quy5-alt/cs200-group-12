**Manage Member Records**

**Use Case:** Manage Member Records Context: Lets a ChocAn Operator add, update, or delete member records at the Data Center. 
**Actors:** ChocAn Operator

**Main Success Scenario:**

Operator selects "Manage Member Records" from the Data Center menu.
System displays the options: Add Member, Update Member, Delete Member, Exit.
Operator selects an option. 3.1. System performs the corresponding extension use case.
System displays a confirmation message and returns to step 2.
Operator selects Exit.
System returns to the Data Center main menu.

**Extensions:** 
3a. Operator selects Add Member: system executes Add Member Record. 
3b. Operator selects Update Member: system executes Update Member Record. 
3c. Operator selects Delete Member: system executes Delete Member Record.
3d. Operator enters an invalid option: system displays an error message and returns to step 2.