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
  private services: any;
  constructor(private httpClient: HttpClient) {
    this.httpClient = httpClient;
  }

  ngOnInit() {
    const headers = new HttpHeaders().set(
      "Authorization",
      "bearer eyJhbGciOiJSUzI1NiIsImprdSI6Imh0dHBzOi8vdWFhLmNsb3VkLnBjZnRlc3QuY29tL3Rva2VuX2tleXMiLCJraWQiOiJrZXktMSIsInR5cCI6IkpXVCJ9.eyJqdGkiOiI4NzIwM2JiZWJjOTc0OGJlYmMzMmZmMWZkMDhkZmM5YiIsInN1YiI6IjM4Y2M1ZWQ2LTY3YWEtNDQyNC04MjBkLTNlYWZjYjE1ZjFjMiIsInNjb3BlIjpbImNsb3VkX2NvbnRyb2xsZXIucmVhZCIsInBhc3N3b3JkLndyaXRlIiwiY2xvdWRfY29udHJvbGxlci53cml0ZSIsIm9wZW5pZCIsInVhYS51c2VyIl0sImNsaWVudF9pZCI6ImNmIiwiY2lkIjoiY2YiLCJhenAiOiJjZiIsImdyYW50X3R5cGUiOiJwYXNzd29yZCIsInVzZXJfaWQiOiIzOGNjNWVkNi02N2FhLTQ0MjQtODIwZC0zZWFmY2IxNWYxYzIiLCJvcmlnaW4iOiJsZGFwIiwidXNlcl9uYW1lIjoic2R1dHRhIiwiZW1haWwiOiJzdXNoYW50YS5kdXR0YUBwaGlsaXBzLmNvbSIsImF1dGhfdGltZSI6MTU1NTc0ODg5MSwicmV2X3NpZyI6IjE4ZDhhNGI1IiwiaWF0IjoxNTU1ODU4NDc2LCJleHAiOjE1NTU4NTkwNzYsImlzcyI6Imh0dHBzOi8vdWFhLmNsb3VkLnBjZnRlc3QuY29tL29hdXRoL3Rva2VuIiwiemlkIjoidWFhIiwiYXVkIjpbImNsb3VkX2NvbnRyb2xsZXIiLCJwYXNzd29yZCIsImNmIiwidWFhIiwib3BlbmlkIl19.dOIMRwRjOqbY-ElREJPnC1H2J2rgLRsflAsOtg73tRzI40BZ8Fcb1S4IBkRnJX7O8m8t7mHM4fIgQ8Bv6F9U-Ld4CqxTfBzpnCTi5-GO1e4GyUebSduMNHbZ7PPIqyOWm6vbO_Pa3Kkz-Vx5qrAXO0HYCzYwmR7b_IiYzoZhhO0RmWAQxoXEUlzIvRTWifa7mabHrAhx7AQNbHdtjrzTa-lNw8W70CVXhPwW2-NEA2AeYlRiDLV45n9tJkF_uGf8nPS4xAZ-VXuLsbsLfHz2Jy_MfWtxFmGV_pLtBz5tzO7nBsvN9d31JavQzEdk8YcWObwIJU_3diDOMaJssVIcTg"
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

    // this.httpClient
    //   .get<any>("http://localhost:8080/cfdashboard/services/", httpOptions)
    //   .subscribe(
    //     res => {
    //       console.log(res['body'].resources);
    //       this.services = res['body'].resources;
    //     },
    //     (err: HttpErrorResponse) => {
    //       console.log(err.message);
    //     }
    //   );
    this.httpClient
      .get("../../../assets/mockjson/services.json")
      .subscribe(data => {
        this.services = data['resources'];
        console.log(this.services)
      });

  }
}
