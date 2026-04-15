import { memo, useMemo, useRef } from "react";
import { Canvas, useFrame } from "@react-three/fiber";
import * as THREE from "three";

function createCloud(count, spread) {
  const positions = new Float32Array(count * 3);
  for (let i = 0; i < count; i += 1) {
    const radius = spread * (0.35 + Math.random() * 0.65);
    const theta = Math.random() * Math.PI * 2;
    const phi = Math.acos(2 * Math.random() - 1);

    positions[i * 3] = radius * Math.sin(phi) * Math.cos(theta);
    positions[i * 3 + 1] = radius * Math.sin(phi) * Math.sin(theta);
    positions[i * 3 + 2] = radius * Math.cos(phi);
  }
  return positions;
}

function ParticleCloud({ count, spread, speed, color }) {
  const pointsRef = useRef(null);
  const positions = useMemo(() => createCloud(count, spread), [count, spread]);

  useFrame((state, delta) => {
    if (!pointsRef.current) {
      return;
    }

    pointsRef.current.rotation.y += delta * speed;
    pointsRef.current.rotation.x = Math.sin(state.clock.elapsedTime * 0.15) * 0.08;
  });

  return (
    <points ref={pointsRef}>
      <bufferGeometry>
        <bufferAttribute
          attach="attributes-position"
          args={[positions, 3]}
          count={positions.length / 3}
          itemSize={3}
        />
      </bufferGeometry>
      <pointsMaterial
        color={new THREE.Color(color)}
        size={0.028}
        sizeAttenuation
        transparent
        opacity={0.78}
        depthWrite={false}
      />
    </points>
  );
}

function BackdropScene({ sceneQuality }) {
  const settings = {
    high: { count: 420, dpr: [1, 1.5] },
    medium: { count: 260, dpr: [1, 1.25] },
    low: { count: 120, dpr: [1, 1] }
  }[sceneQuality] || { count: 180, dpr: [1, 1.2] };

  return (
    <Canvas
      dpr={settings.dpr}
      gl={{ antialias: false, alpha: true, powerPreference: "high-performance" }}
      camera={{ position: [0, 0, 4.5], fov: 58 }}
    >
      <color attach="background" args={["#070b14"]} />
      <fog attach="fog" args={["#070b14", 2.4, 7.8]} />
      <ambientLight intensity={0.32} />
      <pointLight position={[1.8, 2.2, 2.8]} intensity={2.2} color="#3dd6ff" />
      <pointLight position={[-1.8, -1.4, 2]} intensity={1.8} color="#7c8cff" />
      <ParticleCloud count={settings.count} spread={2.45} speed={0.08} color="#3dd6ff" />
      <ParticleCloud count={Math.floor(settings.count * 0.62)} spread={1.55} speed={-0.06} color="#7c8cff" />
    </Canvas>
  );
}

export const WebGLBackdrop = memo(function WebGLBackdrop({ enabled, sceneQuality }) {
  if (!enabled) {
    return <div className="fallback-backdrop" aria-hidden="true" />;
  }

  return (
    <div className="webgl-backdrop" aria-hidden="true">
      <BackdropScene sceneQuality={sceneQuality} />
    </div>
  );
});
