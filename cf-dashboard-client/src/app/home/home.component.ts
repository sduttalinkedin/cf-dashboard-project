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
  private organiztions : any;
  constructor(private httpClient: HttpClient) {}

  ngOnInit() {
    const headers = new HttpHeaders().set(
      "Authorization",
      "bearer eyJhbGciOiJSUzI1NiIsImprdSI6Imh0dHBzOi8vdWFhLmNsb3VkLnBjZnRlc3QuY29tL3Rva2VuX2tleXMiLCJraWQiOiJrZXktMSIsInR5cCI6IkpXVCJ9.eyJqdGkiOiI5YWUyYjRkNjlmZDE0OTk2OTcwZTc1NDhhMTBmYTE4NCIsInN1YiI6IjM4Y2M1ZWQ2LTY3YWEtNDQyNC04MjBkLTNlYWZjYjE1ZjFjMiIsInNjb3BlIjpbImNsb3VkX2NvbnRyb2xsZXIucmVhZCIsInBhc3N3b3JkLndyaXRlIiwiY2xvdWRfY29udHJvbGxlci53cml0ZSIsIm9wZW5pZCIsInVhYS51c2VyIl0sImNsaWVudF9pZCI6ImNmIiwiY2lkIjoiY2YiLCJhenAiOiJjZiIsImdyYW50X3R5cGUiOiJwYXNzd29yZCIsInVzZXJfaWQiOiIzOGNjNWVkNi02N2FhLTQ0MjQtODIwZC0zZWFmY2IxNWYxYzIiLCJvcmlnaW4iOiJsZGFwIiwidXNlcl9uYW1lIjoic2R1dHRhIiwiZW1haWwiOiJzdXNoYW50YS5kdXR0YUBwaGlsaXBzLmNvbSIsImF1dGhfdGltZSI6MTU1NTgyNzAzNCwicmV2X3NpZyI6IjE4ZDhhNGI1IiwiaWF0IjoxNTU1ODMyMTU1LCJleHAiOjE1NTU4MzI3NTUsImlzcyI6Imh0dHBzOi8vdWFhLmNsb3VkLnBjZnRlc3QuY29tL29hdXRoL3Rva2VuIiwiemlkIjoidWFhIiwiYXVkIjpbImNsb3VkX2NvbnRyb2xsZXIiLCJwYXNzd29yZCIsImNmIiwidWFhIiwib3BlbmlkIl19.loXKjVcLn3RmIlUXDILYAyrJloF_dY3tPKLH-0AuG64wUKWRh8pZVdPDtign_zTGP5QauqjU8uzeyew8AXfUwe81KofliuUatKLpKVi6G5Z0ZuWYm84NX4nmHTgABwWn-6U-CF97ililkZwWsXGalV4TuQTo6aOO_gkhP7nMbOg86xGGl7NlugaMaF9_M4CMHIS5kDAXsziMn-t7KM3-nb83Ln1CjI0_tIXBJ-rMXojocJxTcx1NiPrIYq7tsr0CHj-_qyNA96c57DwMLHHToyiZZbEC146frIDI9Iwo84tvBx1KatjONzn8yN6TZphc6WtX9JE41Im5-Pv7NkNjCA"
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
            this.organiztions = res['body'];
            console.log(this.organiztions);
        },
        (err: HttpErrorResponse) => {
          console.log(err.message);
        }
      );
  }
}
