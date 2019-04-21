import { Component, OnInit } from "@angular/core";
import {
  HttpClient,
  HttpParams,
  HttpHeaders,
  HttpResponse,
  HttpRequest,
  HttpErrorResponse
} from "@angular/common/http";

@Component({
  selector: "app-home",
  templateUrl: "./home.component.html",
  styleUrls: ["./home.component.css"]
})
export class HomeComponent implements OnInit {
  private services : any;
  constructor(private httpClient: HttpClient) {}

  ngOnInit() {
    const headers = new HttpHeaders().set(
      "Authorization",
      "bearer eyJhbGciOiJSUzI1NiIsImprdSI6Imh0dHBzOi8vdWFhLmNsb3VkLnBjZnRlc3QuY29tL3Rva2VuX2tleXMiLCJraWQiOiJrZXktMSIsInR5cCI6IkpXVCJ9.eyJqdGkiOiI2MGYyZjZmOGI4N2M0M2U5YTFjNzA0ZDQwNmJlN2Q1OCIsInN1YiI6IjM4Y2M1ZWQ2LTY3YWEtNDQyNC04MjBkLTNlYWZjYjE1ZjFjMiIsInNjb3BlIjpbImNsb3VkX2NvbnRyb2xsZXIucmVhZCIsInBhc3N3b3JkLndyaXRlIiwiY2xvdWRfY29udHJvbGxlci53cml0ZSIsIm9wZW5pZCIsInVhYS51c2VyIl0sImNsaWVudF9pZCI6ImNmIiwiY2lkIjoiY2YiLCJhenAiOiJjZiIsImdyYW50X3R5cGUiOiJwYXNzd29yZCIsInVzZXJfaWQiOiIzOGNjNWVkNi02N2FhLTQ0MjQtODIwZC0zZWFmY2IxNWYxYzIiLCJvcmlnaW4iOiJsZGFwIiwidXNlcl9uYW1lIjoic2R1dHRhIiwiZW1haWwiOiJzdXNoYW50YS5kdXR0YUBwaGlsaXBzLmNvbSIsImF1dGhfdGltZSI6MTU1NTg0NTMwMCwicmV2X3NpZyI6IjE4ZDhhNGI1IiwiaWF0IjoxNTU1ODQ5NzE5LCJleHAiOjE1NTU4NTAzMTksImlzcyI6Imh0dHBzOi8vdWFhLmNsb3VkLnBjZnRlc3QuY29tL29hdXRoL3Rva2VuIiwiemlkIjoidWFhIiwiYXVkIjpbImNsb3VkX2NvbnRyb2xsZXIiLCJwYXNzd29yZCIsImNmIiwidWFhIiwib3BlbmlkIl19.rh284dam5qIVcAU8XRduWzNjm203TWP_KYPFGZ4w_DRth913kJw21gQnPhh0SEER20maWlG71YWs-UTip1B0foLO94pZ8Oa9EvcHjra1hlClcHZbZM687-eexr8q19VPzNdascSKi4WZtjCiI2EbnfRDvk0jdb0Yidpwa8wl5H2Lxoklhx4jA8o34Ib6m4fVa1PEYsbDbXl4Dg4L3f2KzDHi99KIbQAhU-W5U2X8o4Cwyopz_XaesQ-DPC6P4g7yTC--FTxUt1tSZnLryD3B_I18wkA1jUiSEBXAUlnK8Dhd_KiCck4Y48Mbe1JOudYBfdhow7SKVjnPAclyuOZzvg"
    );
    // Initialize Params Object
    let params = new HttpParams();
    // Begin assigning parameters
    params = params.append(
      "space-guid",
      "328d99a7-7807-483f-ae0e-c83ed7fa76f2"
    );

    const httpOptions = {
      headers: headers,
      observe: "response" as "response",
      params: params
    };

    this.httpClient
      .get<any>("http://localhost:8080/cfdashboard/services/", httpOptions)
      .subscribe(
        res => {
          console.log(res['body'].resources);
          this.services = res['body'].resources;
            
        },
        (err: HttpErrorResponse) => {
          console.log(err.message);
        }
      );
  }
}
