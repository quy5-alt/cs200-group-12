Use Case: Record Service
Context: Allows a provider to record a service provided to a validated member. The provider must have already validated the member before recording a service. This Use Case includes "Validate Member" and "Handle Invalid Service Code."
Actors: Provider
Main Success Scenario:
1. Provider validates the member using the "Validate Member" Use Case.
2. Provider enters the provider number, date of service, and six-digit service code.
3. System checks the service code against the list of valid service codes.
4. System records the service, including member number, provider number, service code, and date of service, in the current service file.
5. System displays the member's name, service code, and fee for the service on the terminal.
Extensions:
    3a. Service code does not exist: System executes "Handle Invalid Service Code" Use Case, and the Use Case ends.
    4a. Member is invalid or suspended: System displays the corresponding message from "Validate Member," and the Use Case ends.
