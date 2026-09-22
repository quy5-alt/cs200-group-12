# Use Case: Handle Invalid Service Code
 
**Context:** Handles the situation where a provider enters a service code that does not match any code in the list of valid ChocAn services. This Use Case is included by "Record Service."
 
**Actors:** Provider
 
**Main Success Scenario:**
1. Provider enters a service code as part of recording a service.
2. System checks the entered service code against the list of valid service codes.
3. System determines the service code does not exist.
4. System displays "Invalid service code" on the terminal.
5. System prompts the provider to re-enter the service code.
**Extensions:**
- 2a. Provider re-enters a valid service code: System resumes the "Record Service" Use Case at step 4.
- 2b. Provider cancels the entry: System returns to the terminal's main screen, and the Use Case ends.
