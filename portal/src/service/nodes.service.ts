import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import BACKEND_HOST from "../constant";
import GetNodesResp from "../module/GetNodesResp";
import {Injectable} from "@angular/core";

@Injectable({providedIn: "root"})
export class NodesService {

  constructor(private http: HttpClient) { }

  getNodes(path: String): Observable<String[]> {
    return this.http.post<GetNodesResp>(BACKEND_HOST + '/api/zookeeper/get-nodes', {
      path: path
    }).pipe(map(resp => resp.nodes));
  }
}
