package com.dockervolumeblobstorage.controller;

import com.dockervolumeblobstorage.service.BlobStorageDownloadFileToDockerVolume;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/madhura/alone")
@Slf4j
public class BlobStorageDownloadFileToDockerVolumeController {

    @Autowired
    private BlobStorageDownloadFileToDockerVolume blobStorageDownloadFileToDockerVolume;

    @GetMapping(value = "/blob-download")
    private String blobDownload() {
        log.info("file was downloading");
      return blobStorageDownloadFileToDockerVolume.blobStorageDownloadFileToDockerVolume();
    }
}
