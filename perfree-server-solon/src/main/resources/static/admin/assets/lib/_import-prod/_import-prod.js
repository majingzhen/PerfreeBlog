import{m as t}from"../../index-Cbj4A6nx.js";const p=(i,r)=>i.pluginId&&i.pluginIsDev?t(()=>import(`${i.pluginFrontDevAddress}/plugin/${i.pluginId}/src/modules/${r}/index.js`),[]):i.pluginId?t(()=>import(`/api/plugin-static/${i.pluginId}/modules/${r}/index.js`),[]):t(()=>import(`/modules/${r}/index.js`),[]);export{p as default};