import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import BACKEND_HOST from "../constant";
import GetNodesResp from "../module/GetNodesResp";
import {Injectable} from "@angular/core";
import GetNodeResp from "../module/GetNodeResp";
import SupportDecodeComponentListResp from "../module/SupportDecodeComponentListResp";
import SupportDecodeNamespaceListResp from "../module/SupportDecodeNamespaceListResp";

@Injectable({providedIn: "root"})
export class NodesService {

  constructor(private http: HttpClient) { }

  getNodes(path: String): Observable<String[]> {
    return this.http.post<GetNodesResp>(BACKEND_HOST + '/api/zookeeper/get-nodes', {
      path: path
    }).pipe(map(resp => resp.nodes));
  }

  getNodeContent(path: String): Observable<String> {
    return this.http.post<GetNodeResp>(BACKEND_HOST + '/api/zookeeper/get-node', {
      path: path
    }).pipe(map(resp => resp.data));
  }

  getNodeHexContent(path: String): Observable<String> {
    return this.http.post<GetNodeResp>(BACKEND_HOST + '/api/zookeeper/get-node?codec=hex', {
      path: path
    }).pipe(map(resp => resp.data));
  }

  getNodeDecodeContent(path: String, decodeComponent: string, decodeNamespace: string): Observable<String> {
    return this.http.post<GetNodeResp>(BACKEND_HOST +
      '/api/zookeeper/get-node-decode?decodeComponent=' + decodeComponent + '&decodeNamespace=' + decodeNamespace, {
      path: path
    }).pipe(map(resp => resp.data))
  }

  getDecodeComponents(): Observable<String[]> {
    return this.http.get<SupportDecodeComponentListResp>(BACKEND_HOST + '/api/zookeeper/decode-components')
      .pipe(map(resp => resp.supportDecodeComponents))
  }

  getDecodeNamespaces(): Observable<String[]> {
    return this.http.get<SupportDecodeNamespaceListResp>(BACKEND_HOST + '/api/zookeeper/decode-namespaces')
      .pipe(map(resp => resp.supportDecodeNamespaces))
  }
}
